package com.mendel.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatusResponse {
    private String status;
}
