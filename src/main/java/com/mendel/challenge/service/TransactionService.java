package com.mendel.challenge.service;

import com.mendel.challenge.exception.ResourceNotFoundException;
import com.mendel.challenge.model.TotalSumTO;
import com.mendel.challenge.model.Transaction;
import com.mendel.challenge.model.TransactionNode;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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

    public List<Long> getTransactionsByType(String type) {
        List<Long> result = transactionTree.values().stream()
                .filter(n -> type.equals(n.getType()))
                .map(n -> n.getId())
                .collect(Collectors.toList());

        return result;
    }


    public TotalSumTO getTotalSumById(Long transactionId) {

        TransactionNode node = transactionTree.get(transactionId);
        TotalSumTO result = null;
        if(node != null){
            result = TotalSumTO.builder().sum(node.getTotalAmount()).build();
        }
        throw new ResourceNotFoundException("no se encontro recurso");
    }
}