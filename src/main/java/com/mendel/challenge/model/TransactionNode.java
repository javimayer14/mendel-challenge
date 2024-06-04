package com.mendel.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionNode {
    private  Long id;
    private  String type;
    private  double amount;
    private  Long parentId;
    private  List<TransactionNode> children = new ArrayList<>();
    private double totalAmount;

    public TransactionNode(Long id, String type, double amount, Long parentId) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.parentId = parentId;
        this.totalAmount = amount;
    }

    public void addChild(TransactionNode child, Map<Long, TransactionNode> transactionTree) {
        children.add(child);
        updateTotalAmount(child.totalAmount, transactionTree);
    }

    private void updateTotalAmount(double amountToAdd, Map<Long, TransactionNode> transactionTree) {
        this.totalAmount += amountToAdd;
        if (this.parentId != null) {
            TransactionNode parentNode = transactionTree.get(this.parentId);
            if (parentNode != null) {
                parentNode.updateTotalAmount(amountToAdd, transactionTree);
            }
        }
    }

}