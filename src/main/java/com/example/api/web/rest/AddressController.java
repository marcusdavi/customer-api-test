package com.example.api.web.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.domain.Customer;
import com.example.api.domain.dto.AddressRequestDTO;
import com.example.api.service.AddressService;

@RestController
@RequestMapping("/addresses")
public class AddressController {

	private AddressService service;

	@Autowired
	public AddressController(AddressService service) {
		this.service = service;
	}
	
	@PostMapping("/{customerId}")
	public ResponseEntity<Customer> addCustomerAddress(@PathVariable Long customerId, @Valid @RequestBody AddressRequestDTO addressDTO){
		
		return ResponseEntity.ok(service.addCustomerAddress(customerId, addressDTO));
	}

	

}
