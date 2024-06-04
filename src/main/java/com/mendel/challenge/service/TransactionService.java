package com.mendel.challenge.service;

import com.mendel.challenge.model.Transaction;
import com.mendel.challenge.model.TransactionNode;
import com.mendel.challenge.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class TransactionService {

    private final Map<Long, TransactionNode> transactionTree = new HashMap<>();

    public Transaction createTransaction(Transaction transaction, Long transactionId) {

        TransactionNode newNode = new TransactionNode(transactionId, transaction.getType(), transaction.getAmount(), transaction.getParentId());
        transactionTree.put(transactionId, newNode);

        if (transaction.getParentId() != null) {
            TransactionNode parentNode = transactionTree.get(transaction.getParentId());
            if (parentNode != null) {
                parentNode.addChild(newNode, transactionTree);
            }
        }

        return new Transaction();
    }


}