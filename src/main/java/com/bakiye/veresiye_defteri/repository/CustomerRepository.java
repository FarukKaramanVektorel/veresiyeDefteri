package com.bakiye.veresiye_defteri.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bakiye.veresiye_defteri.data.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
