package com.bakiye.veresiye_defteri.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bakiye.veresiye_defteri.data.dto.CustomerDto;
import com.bakiye.veresiye_defteri.data.dto.CustomerWithDeptDto;
import com.bakiye.veresiye_defteri.exceptions.SuccessResponse;
import com.bakiye.veresiye_defteri.service.CustomerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

	private final CustomerService customerService;

    @PostMapping("/add")
    public ResponseEntity<SuccessResponse> addCustomer(@RequestBody CustomerDto customerDto) {
        SuccessResponse response = customerService.addCustomer(customerDto);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/update")
    public ResponseEntity<SuccessResponse> updateCustomer(@RequestBody CustomerDto customerDto) {
        SuccessResponse response = customerService.updateCustomer(customerDto.getId(), customerDto);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/totalDebt")
    public ResponseEntity<CustomerWithDeptDto> getCustomerWithTotalDebt(@RequestBody Long customerId) {
        CustomerWithDeptDto customerWithDeptDto = customerService.getCustomerWithTotalDebt(customerId);
        return ResponseEntity.ok(customerWithDeptDto);
    }
    @GetMapping("/list")
    public ResponseEntity<List<CustomerWithDeptDto>> getAllCustomersWithTotalDebt() {
        List<CustomerWithDeptDto> customers = customerService.getAllCustomersWithTotalDebt();
        return ResponseEntity.ok(customers);
    }

   
}

