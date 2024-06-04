package com.mendel.challenge.controller;

import com.mendel.challenge.model.TotalSumTO;
import com.mendel.challenge.model.Transaction;
import com.mendel.challenge.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/{transactionId}")
    public ResponseEntity<Transaction> createTransaction(@PathVariable("transactionId") Long tansactionId, @RequestBody Transaction transaction) {
            Transaction createdTransaction = transactionService.createTransaction(transaction, tansactionId);
            return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    @GetMapping("/types/{type}")
    public ResponseEntity<List<Long>> createTransaction(@PathVariable("type") String type) {
            List<Long> transactionsByType = transactionService.getTransactionsByType(type);
            return new ResponseEntity<>(transactionsByType, HttpStatus.OK);
    }

    @GetMapping("/sum/{transactionId}")
    public ResponseEntity<TotalSumTO> createTransaction(@PathVariable("transactionId") Long transactionId) {
            TotalSumTO totalSum = transactionService.getTotalSumById(transactionId);
            return new ResponseEntity<>(totalSum, HttpStatus.OK);

    }

}