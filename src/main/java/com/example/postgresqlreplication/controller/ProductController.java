package com.example.postgresqlreplication.controller;

import com.example.postgresqlreplication.dto.ProductResponse;
import com.example.postgresqlreplication.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private static final int MAX_COUNT = 1000;

    private final ProductService productService;

    @PostMapping("/generate")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<ProductResponse>> generateProducts(@RequestParam int count) {
        if (count <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "count must be greater than 0");
        }
        if (count > MAX_COUNT) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "count must not exceed " + MAX_COUNT);
        }

        return new ResponseEntity<>(productService.generate(count), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductResponse> getProductById(@PathVariable int id) {
        return ResponseEntity.ok(productService.getById(id));
    }
}
