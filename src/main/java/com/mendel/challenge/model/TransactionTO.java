package com.mendel.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionTO {
    private Long id;
    private String type;
    private double amount;
    private Long parentId;
}