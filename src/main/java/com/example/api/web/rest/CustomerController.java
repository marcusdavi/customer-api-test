package com.example.api.web.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.api.domain.Customer;
import com.example.api.domain.dto.CustomerRequestDto;
import com.example.api.service.CustomerService;
import com.sun.istack.NotNull;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	private CustomerService service;

	@Autowired
	public CustomerController(CustomerService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<Page<Customer>> findAll(
			@PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
		return ResponseEntity.ok(service.findAll(pageable));
	}

	@GetMapping("/{id}")
	public Customer findById(@PathVariable Long id) {
		return service.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
	}

	@GetMapping("/search")
	public ResponseEntity<Page<Customer>> search(@RequestParam(required = false) String name,
			@Valid @RequestParam(required = false) String gender, @Valid @RequestParam(required = false) String email,
			@PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
		return ResponseEntity.ok(service.search(name, email, gender, pageable));
	}

	@PostMapping
	public ResponseEntity<Customer> create(@RequestBody @Valid CustomerRequestDto customerDTO) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(customerDTO));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Customer> update(@PathVariable Long id, @RequestBody @Valid CustomerRequestDto customerDTO) {
		return ResponseEntity.ok(service.update(id, customerDTO));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@Valid @PathVariable @NotNull Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
