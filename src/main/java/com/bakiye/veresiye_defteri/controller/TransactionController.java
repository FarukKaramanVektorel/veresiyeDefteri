package com.bakiye.veresiye_defteri.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bakiye.veresiye_defteri.data.dto.TransactionDto;
import com.bakiye.veresiye_defteri.exceptions.SuccessResponse;
import com.bakiye.veresiye_defteri.service.TransactionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/add")
    public ResponseEntity<SuccessResponse> addTransaction(@RequestBody TransactionDto transactionDto) {
        SuccessResponse response = transactionService.addTransaction(transactionDto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<SuccessResponse> updateTransaction(@RequestBody TransactionDto transactionDto) {
        SuccessResponse response = transactionService.updateTransaction(transactionDto.getId(),transactionDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<SuccessResponse> deleteTransaction(@RequestBody Long id) {
        SuccessResponse response = transactionService.deleteTransaction(id);
        return ResponseEntity.ok(response);
    }
}
