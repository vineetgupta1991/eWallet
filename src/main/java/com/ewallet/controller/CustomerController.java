package com.ewallet.controller;

import com.ewallet.exception.CustomerDoesNotExistException;
import com.ewallet.model.dto.CustomerDto;
import com.ewallet.model.entity.Customer;
import com.ewallet.repository.CustomerRepository;
import io.swagger.annotations.*;
import org.apache.commons.lang3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Api(tags = "Customer")
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;

	@ApiOperation(value = "Find All Customers")
	@ApiImplicitParam(name = "language", dataType = "string", paramType = "header", value = "", defaultValue = "eng")
	@ApiResponses(value = {
		@ApiResponse(code = 400, message = "Validation Failed"),
		@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@GetMapping("/api/customer")
	public List<Customer> findAllCustomers(@RequestParam(name = "email", required = false) String email) {

		if (email != null) {
			return customerRepository.findCustomerByEmail(email);
		}

		return customerRepository.findAll();
	}

	@ApiOperation(value = "Find Customer by Id")
	@ApiImplicitParam(name = "language", dataType = "string", paramType = "header", value = "", defaultValue = "eng")
	@ApiResponses(value = {
		@ApiResponse(code = 400, message = "Validation Failed"),
		@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@GetMapping("/api/customer/{userId}")
	public Customer findCustomerById(@PathVariable("userId") int id) throws CustomerDoesNotExistException {

		return customerRepository.findById(id).orElseThrow(() -> new CustomerDoesNotExistException(id));
	}

	@ApiOperation(value = "Create Customer")
	@ApiImplicitParam(name = "language", required = true, dataType = "string", paramType = "header", value = "", defaultValue = "eng")
	@ApiResponses(value = {
		@ApiResponse(code = 400, message = "Validation Failed"),
		@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@PostMapping("/api/customer")
	public Customer createCustomer(@RequestBody CustomerDto customer) throws Exception {
		if(StringUtils.isNoneBlank(customer.getFname(), customer.getLname(), customer.getEmail()) ) {
			return customerRepository.save(new Customer(customer.getFname(), customer.getLname(), customer.getEmail()));
		} else {
			throw new Exception("Please Enter Customer Details!");
		}

	}

}
