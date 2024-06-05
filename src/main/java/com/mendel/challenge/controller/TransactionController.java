package com.mendel.challenge.controller;

import com.mendel.challenge.model.StatusResponse;
import com.mendel.challenge.model.TotalSumTO;
import com.mendel.challenge.model.TransactionTO;
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
    public ResponseEntity<StatusResponse> createTransaction(@PathVariable("transactionId") Long tansactionId, @RequestBody TransactionTO transaction) {
            StatusResponse statusResponse = transactionService.createTransaction(transaction, tansactionId);
            return new ResponseEntity<>(statusResponse, HttpStatus.CREATED);
    }

    @GetMapping("/types/{type}")
    public ResponseEntity<List<Long>> getTransactionsByType(@PathVariable("type") String type) {
            List<Long> transactionsByType = transactionService.getTransactionsByType(type);
            return new ResponseEntity<>(transactionsByType, HttpStatus.OK);
    }

    @GetMapping("/sum/{transactionId}")
    public ResponseEntity<TotalSumTO> getTotalSumById(@PathVariable("transactionId") Long transactionId) {
            TotalSumTO totalSum = transactionService.getTotalSumById(transactionId);
            return new ResponseEntity<>(totalSum, HttpStatus.OK);

    }

}