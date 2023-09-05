package com.example.demo.controller;

import com.example.demo.database.TransactionDatabase;
import com.example.demo.dto.Transactions;
import com.example.demo.service.RewardCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {
    @Autowired
    private TransactionDatabase transactionRepository;

    @Autowired
    private RewardCalculator rewardService;

    @GetMapping("/customer/{customerId}/month/{month}")
    public int calculateRewardPointsForMonth(@PathVariable Long customerId, @PathVariable int month) {
        List<Transactions> transactions = transactionRepository.findByCustomerId(customerId);
        double totalRewardPoints = 0;
        LocalDate currentDate = LocalDate.now();

        for (Transactions transaction : transactions) {
            if (transaction.getTransactionDate().getMonthValue() == month) {
                totalRewardPoints += rewardService.calculateRewardPoints(transaction.getAmount());
            }
        }

        return (int) totalRewardPoints;
    }

    @GetMapping("/customer/{customerId}/total")
    public int calculateTotalRewardPoints(@PathVariable Long customerId) {
        List<Transactions> transactions = transactionRepository.findByCustomerId(customerId);
        double totalRewardPoints = 0;

        for (Transactions transaction : transactions) {
            totalRewardPoints += rewardService.calculateRewardPoints(transaction.getAmount());
        }

        return (int) totalRewardPoints;
    }
}
