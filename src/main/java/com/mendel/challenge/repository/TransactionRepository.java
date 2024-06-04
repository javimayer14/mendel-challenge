package com.mendel.challenge.repository;

import com.mendel.challenge.model.Transaction;
import com.mendel.challenge.model.TransactionNode;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class TransactionRepository {

    public static final Map<Long, Transaction> transactionStore = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();



    public Transaction save(Transaction transaction) {
        if (transaction.getId() == null) {
            transaction.setId(idGenerator.incrementAndGet());
        }
        transactionStore.put(transaction.getId(), transaction);
        return transaction;
    }

    public List<Transaction> findByType(String type) {
        return transactionStore.values().stream()
                .filter(transaction -> transaction.getType().equals(type))
                .collect(Collectors.toList());
    }

    public Optional<Transaction> findById(Long id) {
        return Optional.ofNullable(transactionStore.get(id));
    }

    public List<Transaction> findByParentId(Long parentId) {
        return transactionStore.values().stream()
                .filter(transaction -> parentId.equals(transaction.getParentId()))
                .collect(Collectors.toList());
    }
}