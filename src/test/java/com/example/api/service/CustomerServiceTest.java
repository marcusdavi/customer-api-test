package com.example.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.api.domain.Customer;
import com.example.api.domain.dto.CustomerRequestDto;
import com.example.api.exceptions.ResourceNotFoundException;
import com.example.api.repository.CustomerRepository;

@ExtendWith(SpringExtension.class)
public class CustomerServiceTest {

	private static final long ID_NOT_EXISTS = 123L;

	private static final String NAME_1 = "Joao";
	private static final String NAME_2 = "Maria";

	private static final long ID = 1l;

	@InjectMocks
	private CustomerService service;

	@Mock
	private CustomerRepository repository;

	private PageRequest pageable = PageRequest.of(0, 10);

	@Test
	public void findByAllOkTest() {
		List<Customer> customers = new ArrayList<>();
		customers.add(Customer.builder().name(NAME_1).build());
		customers.add(Customer.builder().name(NAME_2).build());

		Mockito.when(repository.findAll(pageable)).thenReturn(new PageImpl<>(customers));

		Page<Customer> response = service.findAll(pageable);

		assertEquals(2, response.getTotalElements());
		assertEquals(1, response.getTotalPages());
	}

	@Test
	public void findByIdOkTest() {
		Customer customer = Customer.builder().id(ID).name(NAME_1).build();

		Mockito.when(repository.findById(ID)).thenReturn(Optional.of(customer));

		Optional<Customer> response = service.findById(ID);

		assertTrue(response.isPresent());
		assertEquals(ID, response.get().getId());
		assertEquals(NAME_1, response.get().getName());

		response = service.findById(ID_NOT_EXISTS);
		assertFalse(response.isPresent());
	}

	@Test
	public void createCustomerOkTest() {
		Customer customer = Customer.builder().id(ID).name(NAME_1).build();
		CustomerRequestDto request = CustomerRequestDto.builder().name(NAME_1).build();

		Mockito.when(repository.save(Mockito.any())).thenReturn(customer);

		Customer response = service.create(request);

		assertEquals(ID, response.getId());
		assertEquals(NAME_1, response.getName());

	}

	@Test
	public void updateCustomerOkTest() {
		Customer customer = Customer.builder().id(ID).name(NAME_1).build();
		CustomerRequestDto request = CustomerRequestDto.builder().name(NAME_1).build();

		Mockito.when(repository.existsById(ID)).thenReturn(true);
		Mockito.when(repository.save(Mockito.any())).thenReturn(customer);

		Customer response = service.update(ID, request);

		assertEquals(ID, response.getId());
		assertEquals(NAME_1, response.getName());

	}

	@Test
	public void updateCustomerNotExistsTest() {
		CustomerRequestDto request = CustomerRequestDto.builder().name(NAME_1).build();

		Mockito.when(repository.existsById(ID)).thenReturn(false);

		try {
			service.update(ID, request);
			fail("This test should give an error.");
		} catch (ResourceNotFoundException e) {
			assertEquals("The customer doesn't exist.", e.getMessage());
		}

	}

	@Test
	public void deleteCustomerNotExistsTest() {
		Mockito.when(repository.existsById(ID)).thenReturn(false);

		try {
			service.delete(ID);
			fail("This test should give an error.");
		} catch (ResourceNotFoundException e) {
			assertEquals("The customer doesn't exist.", e.getMessage());
		}

	}

}
