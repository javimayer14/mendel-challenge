package com.mendel.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data
public class TransactionNode {
    private  Long id;
    private  String type;
    private  double amount;
    private  Long parentId;
    private  List<TransactionNode> children;
    private double totalAmount;

    public TransactionNode(Long id, String type, double amount, Long parentId) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.parentId = parentId;
        this.totalAmount = amount;
        this.children = new ArrayList<>();
    }

    public void addChild(TransactionNode child, Map<Long, TransactionNode> transactionTree) {
        children.add(child);
        updateTotalAmount(child.totalAmount, transactionTree);
    }

    private void updateTotalAmount(double amountToAdd, Map<Long, TransactionNode> transactionTree) {
        this.totalAmount += amountToAdd;
        Optional.ofNullable(parentId)
                .map(transactionTree::get)
                .ifPresent(parentNode -> parentNode.updateTotalAmount(amountToAdd, transactionTree));
    }

}