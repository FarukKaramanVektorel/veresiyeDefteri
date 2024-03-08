
package com.bakiye.veresiye_defteri.data.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private Long id;
    private LocalDate transactionDate;
    private BigDecimal transactionAmount;
    private Long customerId;
    private int transactionType;
}

