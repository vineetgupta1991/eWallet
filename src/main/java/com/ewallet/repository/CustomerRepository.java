package com.ewallet.repository;

import com.ewallet.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	List<Customer> findCustomerByEmail(@Param("email") String email);


}
