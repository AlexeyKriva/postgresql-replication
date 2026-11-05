package com.example.postgresqlreplication.service;

import com.example.postgresqlreplication.dto.ProductResponse;
import com.example.postgresqlreplication.entity.Product;
import com.example.postgresqlreplication.entity.ProductBasePackSize;
import com.example.postgresqlreplication.entity.projection.DatabaseConnectionInfo;
import com.example.postgresqlreplication.exception.ProductNotFoundException;
import com.example.postgresqlreplication.mapper.ProductMapper;
import com.example.postgresqlreplication.repository.ProductBasePackSizeRepo;
import com.example.postgresqlreplication.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static com.example.postgresqlreplication.util.ProductFieldsValueGenerator.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;
    private final ProductBasePackSizeRepo packSizeRepo;
    private final ProductMapper productMapper;

    @Transactional
    public List<ProductResponse> generate(int count) {
        List<ProductBasePackSize> packSizes = packSizeRepo.findAll();

        if (packSizes.isEmpty()) {
            throw new IllegalStateException("No product base pack sizes found in database");
        }

        ThreadLocalRandom random = ThreadLocalRandom.current();

        ExecutorService executor = Executors.newFixedThreadPool((int) Math.ceil(count / 1000.0));

        List<Future<Product>> futureProducts = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            Future<Product> futureProduct = executor.submit(() -> generateProduct(packSizes, random));
            futureProducts.add(futureProduct);
        }

        logTransactionState();

        List<Product> generatedProducts = futureProducts.stream()
                .map(futureProduct -> {
                    try {
                        return futureProduct.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();

        return productMapper.toProductResponses(productRepo.saveAll(generatedProducts));
    }

    private Product generateProduct(List<ProductBasePackSize> packSizes, ThreadLocalRandom random) {
        ProductBasePackSize packSize = packSizes.get(random.nextInt(packSizes.size()));

        Product product = new Product();
        product.setName(generateRandomName(random));
        product.setPackSize(packSize);
        product.setPrice(generateRandomPrice(random));
        product.setSalePrecent(generateRandomSale(random));

        return product;
    }

    @SneakyThrows
    @Transactional(readOnly = true)
    public ProductResponse getById(int id) {
        logTransactionState();

        return productMapper.toProductResponse(productRepo.findById(id).orElseThrow(() ->
                new ProductNotFoundException(id)));
    }

    private void logTransactionState() {
        DatabaseConnectionInfo databaseConnectionInfo = productRepo.getDatabaseConnectionInfo();

        log.warn(
                "POSTGRES TRANSACTION STATE: " +
                        "thread={}, " +
                        "timestamp={}, " +
                        "springReadOnly={}, " +
                        "server={}:{}, " +
                        "replica={}, " +
                        "pid={}, " +
                        "transaction_read_only={}, " +
                        "default_transaction_read_only={}",
                Thread.currentThread().getName(),
                System.nanoTime(),
                TransactionSynchronizationManager.isCurrentTransactionReadOnly(),
                databaseConnectionInfo.getInetServerAddr(),
                databaseConnectionInfo.getInetServerPort(),
                databaseConnectionInfo.getPgIsInRecovery(),
                databaseConnectionInfo.getPgBackendPid(),
                databaseConnectionInfo.getTransactionReadOnly(),
                databaseConnectionInfo.getDefaultTransactionReadOnly()
        );
    }
}
