package com.bakiye.veresiye_defteri.data.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(name = "transaction_date")
	    private LocalDate transactionDate;

	    @Column(name = "transaction_amount")
	    private BigDecimal transactionAmount;

	    @ManyToOne
	    @JoinColumn(name = "customer_id")
	    private Customer customer;

	    @Column(name = "transaction_type")
	    private int transactionType;
}
