package com.bakiye.veresiye_defteri.data.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerWithDeptDto {
	private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String description;
    private BigDecimal totalDebt;
    private Long daysSinceLastTransaction;

   
}
