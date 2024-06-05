package com.mendel.challenge.service;

import com.mendel.challenge.model.StatusResponse;
import com.mendel.challenge.model.TransactionTO;
import com.mendel.challenge.exception.ResourceNotFoundException;
import com.mendel.challenge.model.TotalSumTO;
import com.mendel.challenge.model.TransactionNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TransactionService {

    public static final String NOT_FOUND_MESSAGE = "no se encuentra el recurso";

    private final Map<Long, TransactionNode> transactionTree = new HashMap<>();

    public StatusResponse createTransaction(TransactionTO transaction, Long transactionId) {
        log.info("start transaction creation with id {}", transactionId);
        TransactionNode newNode = new TransactionNode(transactionId, transaction.getType(), transaction.getAmount(), transaction.getParentId());
        transactionTree.put(transactionId, newNode);
        addTransactionToParent(transaction, newNode);
        log.info("finish transaction creation with id {}", transactionId);
        return StatusResponse.builder().status("OK").build();
    }

    public List<Long> getTransactionsByType(String type) {
        log.info("start get transaction with type {}", type);
        List<Long> result = transactionTree.values().stream()
                .filter(n -> type.equals(n.getType()))
                .map(n -> n.getId())
                .collect(Collectors.toList());
        log.info("finish get transaction with type {}", type);
        return result;
    }

    public TotalSumTO getTotalSumById(Long transactionId) {
        log.info("start get total sum for transaction with id {}", transactionId);
        return Optional.ofNullable(transactionTree.get(transactionId))
                .map(node -> TotalSumTO.builder().sum(node.getTotalAmount()).build())
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MESSAGE));
    }

    private void addTransactionToParent(TransactionTO transaction, TransactionNode newNode) {
        Optional.ofNullable(transaction.getParentId())
                .map(transactionTree::get)
                .ifPresent(parentNode -> parentNode.addChild(newNode, transactionTree));
    }


}