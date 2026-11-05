package com.example.postgresqlreplication.repository;

import com.example.postgresqlreplication.entity.Product;
import com.example.postgresqlreplication.entity.projection.DatabaseConnectionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepo extends JpaRepository<Product, Integer> {
    @Query(value = "SELECT inet_server_addr(), inet_server_port(), pg_is_in_recovery(), pg_backend_pid(), " +
            "current_setting('transaction_read_only'), current_setting('default_transaction_read_only')",
            nativeQuery = true)
    DatabaseConnectionInfo getDatabaseConnectionInfo();
}
