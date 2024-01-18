package com.example.api.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import com.example.api.domain.dto.AddressRequestDTO;
import com.example.api.domain.enums.MessageErrorEnum;
import com.example.api.domain.service.cep.dto.AddressCepResponseDTO;
import com.example.api.exceptions.ResourceNotFoundException;

@Service
public class AddressService {

	private CustomerService customerService;
	private CepService cepService;

	@Autowired
	public AddressService(CustomerService customerService, CepService cepService) {
		this.customerService = customerService;
		this.cepService = cepService;
	}

	public Customer addCustomerAddress(Long customerId, AddressRequestDTO addressDTO) {

		// Create this list to avoid two selects customers by id
		Set<Address> addresses = new HashSet<>();

		Optional<Customer> optCustomer = customerService.findById(customerId);
		if (optCustomer.isPresent()) {

			addressDTO.getCeps().forEach(c -> {
				AddressCepResponseDTO cepAddress = cepService.getAddress(c).orElseThrow(
						() -> new ResourceNotFoundException(MessageErrorEnum.ADDRESS_NOT_FOUND.getMessage()));

				Address newAddress = new Address();
				BeanUtils.copyProperties(cepAddress, newAddress);
				addresses.add(newAddress);

			});

			return customerService.addCustomerAddress(optCustomer.get(), addresses);

		} else {
			throw new ResourceNotFoundException(MessageErrorEnum.CUSTOMER_NOT_FOUND.getMessage());
		}

	}
}
