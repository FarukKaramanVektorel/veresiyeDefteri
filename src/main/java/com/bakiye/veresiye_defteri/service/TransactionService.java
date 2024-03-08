package com.bakiye.veresiye_defteri.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.bakiye.veresiye_defteri.data.dto.TransactionDto;
import com.bakiye.veresiye_defteri.data.entity.Customer;
import com.bakiye.veresiye_defteri.data.entity.Transaction;
import com.bakiye.veresiye_defteri.exceptions.ResponseUtil;
import com.bakiye.veresiye_defteri.exceptions.SuccessResponse;
import com.bakiye.veresiye_defteri.repository.CustomerRepository;
import com.bakiye.veresiye_defteri.repository.TransactionRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public BigDecimal getTotalDebtByCustomerId(Long customerId) {
    	BigDecimal totalPayment = transactionRepository.getTotalAmountByCustomerIdAndType(customerId, 0);
    	BigDecimal totalPurchase = transactionRepository.getTotalAmountByCustomerIdAndType(customerId, 1);

    	if (totalPurchase == null) {
    	    totalPurchase = BigDecimal.ZERO;
    	}

    	if (totalPayment == null) {
    	    totalPayment = BigDecimal.ZERO;
    	}

    	return totalPurchase.subtract(totalPayment);

    }

    public SuccessResponse addTransaction(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(transactionDto.getTransactionDate());
        transaction.setTransactionAmount(transactionDto.getTransactionAmount());

        Customer customer = customerRepository.findById(transactionDto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Müşteri bulunamadı"));

        transaction.setCustomer(customer);
        transaction.setTransactionType(transactionDto.getTransactionType());

        Transaction savedTransaction = transactionRepository.save(transaction);

        if (savedTransaction != null) {
            return ResponseUtil.buildSuccessResponse("Hareket başarıyla eklendi.");
        } else {
            return ResponseUtil.buildErrorResponse("Hareket eklenirken bir hata oluştu.");
        }
    }

    public SuccessResponse updateTransaction(Long id, TransactionDto transactionDto) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        if (optionalTransaction.isPresent()) {
            Transaction transaction = optionalTransaction.get();
            transaction.setTransactionDate(transactionDto.getTransactionDate());
            transaction.setTransactionAmount(transactionDto.getTransactionAmount());

            Customer customer = customerRepository.findById(transactionDto.getCustomerId())
                    .orElseThrow(() -> new EntityNotFoundException("Müşteri bulunamadı"));

            transaction.setCustomer(customer);
            transaction.setTransactionType(transactionDto.getTransactionType());

            transactionRepository.save(transaction);
            return ResponseUtil.buildSuccessResponse("Hareket başarıyla güncellendi.");
        } else {
            return ResponseUtil.buildErrorResponse("Hareket bulunamadı.");
        }
    }

    public SuccessResponse deleteTransaction(Long id) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        if (optionalTransaction.isPresent()) {
            transactionRepository.deleteById(id);
            return ResponseUtil.buildSuccessResponse("Hareket başarıyla silindi.");
        } else {
            return ResponseUtil.buildErrorResponse("Hareket bulunamadı.");
        }
    }
    public Long getDaysSinceLastTransaction(Long customerId) {
    	       
        LocalDate today = LocalDate.now(); 
        LocalDate lastTransactionDate = transactionRepository.getLastTransactionDateByCustomerId(customerId);        
        if (lastTransactionDate == null) {           
        	lastTransactionDate= LocalDate.now();
        }
        Long daysSinceLastTransaction = ChronoUnit.DAYS.between(lastTransactionDate, today);
        return daysSinceLastTransaction;
    }
    public LocalDate getLastTransactionDateByCustomerId(Long customerId) {
        return transactionRepository.getLastTransactionDateByCustomerId(customerId);
    }

}
