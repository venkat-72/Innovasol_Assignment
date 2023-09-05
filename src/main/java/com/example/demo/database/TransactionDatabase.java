package com.example.demo.database;


import com.example.demo.dto.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionDatabase extends JpaRepository<Transactions, Long> {
    List<Transactions> findByCustomerId(Long customerId);
}
