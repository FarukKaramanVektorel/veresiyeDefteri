package com.bakiye.veresiye_defteri.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
	private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String description;
}
