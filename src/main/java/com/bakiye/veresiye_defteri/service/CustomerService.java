package com.bakiye.veresiye_defteri.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.bakiye.veresiye_defteri.data.dto.CustomerDto;
import com.bakiye.veresiye_defteri.data.dto.CustomerWithDeptDto;
import com.bakiye.veresiye_defteri.data.entity.Customer;
import com.bakiye.veresiye_defteri.exceptions.ResponseUtil;
import com.bakiye.veresiye_defteri.exceptions.SuccessResponse;
import com.bakiye.veresiye_defteri.repository.CustomerRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final CustomerRepository customerRepository;
	private final ModelMapper modelMapper;
	private final TransactionService transactionService;

	public SuccessResponse addCustomer(CustomerDto customerDto) {

		Customer customer = modelMapper.map(customerDto, Customer.class);
		Customer savedCustomer = customerRepository.save(customer);

		if (savedCustomer != null) {
			return ResponseUtil.buildSuccessResponse("Müşteri başarıyla eklendi.");
		} else {
			return ResponseUtil.buildErrorResponse("Müşteri eklenirken bir hata oluştu.");
		}
	}

	
	public List<CustomerWithDeptDto> getAllCustomersWithTotalDebt() {
	    List<Customer> customers = customerRepository.findAll();
	    List<CustomerWithDeptDto> customerWithDeptDtos = new ArrayList<>();
	    for (Customer customer : customers) {
	        BigDecimal totalDebt = transactionService.getTotalDebtByCustomerId(customer.getId());
	        Long daysSinceLastTransaction;
	        try {
	        	daysSinceLastTransaction = transactionService.getDaysSinceLastTransaction(customer.getId());
	        } catch (Exception e) {
	            daysSinceLastTransaction = 0L; 
	        }
	        CustomerWithDeptDto customerWithDeptDto = new CustomerWithDeptDto(customer.getId(), customer.getFirstName(), customer.getLastName(),
	                customer.getPhone(), customer.getAddress(), customer.getDescription(), totalDebt, daysSinceLastTransaction);
	        customerWithDeptDtos.add(customerWithDeptDto);
	    }
	    return customerWithDeptDtos;
	}



	
	public SuccessResponse updateCustomer(Long id, CustomerDto customerDto) {
		Optional<Customer> optionalCustomer = customerRepository.findById(id);
		if (optionalCustomer.isPresent()) {
			Customer customer = optionalCustomer.get();
			customer.setFirstName(customerDto.getFirstName());
			customer.setLastName(customerDto.getLastName());
			customer.setPhone(customerDto.getPhone());
			customer.setAddress(customerDto.getAddress());
			customer.setDescription(customerDto.getDescription());
			customerRepository.save(customer);
			return ResponseUtil.buildSuccessResponse("Müşteri başarıyla güncellendi.");
		} else {
			return ResponseUtil.buildErrorResponse("Müşteri bulunamadı.");
		}
	}

	public CustomerWithDeptDto getCustomerWithTotalDebt(Long id) {
	    Optional<Customer> optionalCustomer = customerRepository.findById(id);
	    if (optionalCustomer.isPresent()) {
	        Customer customer = optionalCustomer.get();
	        BigDecimal totalDebt = transactionService.getTotalDebtByCustomerId(id);
	        CustomerDto customerDto = new CustomerDto(customer.getId(), customer.getFirstName(), customer.getLastName(),
	                customer.getPhone(), customer.getAddress(), customer.getDescription());
	       
	          
	        Long daysSinceLastTransaction = transactionService.getDaysSinceLastTransaction(id);
	        return new CustomerWithDeptDto(customerDto.getId(), customerDto.getFirstName(), customerDto.getLastName(),
	                customerDto.getPhone(), customerDto.getAddress(), customerDto.getDescription(), totalDebt, daysSinceLastTransaction);
	    } else {
	        throw new EntityNotFoundException("Customer not found with id: " + id);
	    }
	}

	public CustomerDto getCustomer(Long customerId) {
		Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
		if (optionalCustomer.isPresent()) {
			Customer customer = optionalCustomer.get();
			return modelMapper.map(customer, CustomerDto.class);
		} else {
			throw new EntityNotFoundException("Müşteri bulunamadı");
		}
	}

}
