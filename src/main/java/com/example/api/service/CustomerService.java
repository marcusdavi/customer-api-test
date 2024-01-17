package com.example.api.service;

import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import com.example.api.domain.dto.CustomerRequestDto;
import com.example.api.domain.enums.MessageErrorEnum;
import com.example.api.exceptions.ResourceNotFoundException;
import com.example.api.repository.CustomerRepository;

@Service
public class CustomerService {

	private CustomerRepository repository;

	@Autowired
	public CustomerService(CustomerRepository repository, CepService cepService) {
		this.repository = repository;
	}

	public Page<Customer> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Optional<Customer> findById(Long id) {
		return repository.findById(id);
	}

	public Customer create(@Valid CustomerRequestDto customerDTO) {
		Customer newCustomer = new Customer();
		BeanUtils.copyProperties(customerDTO, newCustomer);

		return repository.save(newCustomer);
	}

	public Customer update(Long id, @Valid CustomerRequestDto customerDTO) {
		if (repository.existsById(id)) {
			Customer updateCustomer = new Customer();
			BeanUtils.copyProperties(customerDTO, updateCustomer);
			updateCustomer.setId(id);

			return repository.save(updateCustomer);
		} else {
			throw new ResourceNotFoundException(MessageErrorEnum.CUSTOMER_NOT_FOUND.getMessage());
		}

	}

	public void delete(Long id) {
		if (repository.existsById(id)) {
			repository.deleteById(id);
		} else {
			throw new ResourceNotFoundException(MessageErrorEnum.CUSTOMER_NOT_FOUND.getMessage());
		}

	}

	public Page<Customer> search(String name, String email, String gender, Pageable pageable) {
		Specification<Customer> specification = (root, query, criteriaBuilder) -> {
			Specification<Customer> spec = Specification.where(null);

			if (name != null && !name.isEmpty()) {
				spec = spec.and((root1, query1, criteriaBuilder1) -> criteriaBuilder1
						.like(criteriaBuilder1.lower(root1.get("name")), "%" + name.toLowerCase() + "%"));
			}

			if (gender != null) {
				spec = spec
						.and((root1, query1, criteriaBuilder1) -> criteriaBuilder1.equal(root1.get("gender"), gender));
			}

			if (email != null && !email.isEmpty()) {
				spec = spec.and((root1, query1, criteriaBuilder1) -> criteriaBuilder1
						.like(criteriaBuilder1.lower(root1.get("email")), "%" + email.toLowerCase() + "%"));
			}

			return spec.toPredicate(root, query, criteriaBuilder);
		};

		return repository.findAll(specification, pageable);
	}

	@Transactional
	public Customer addCustomerAddress(Customer customer, Set<Address> addresses) {
		customer.setAddresses(addresses);
		return repository.save(customer);
	}

	public Page<Customer> searchAddress(String uf, String localidade, Pageable pageable) {
		if (StringUtils.hasText(uf) && StringUtils.hasText(localidade)) {
			return repository.findByAddresses_UfAndAddresses_Localidade(uf, localidade, pageable);
		} else if (StringUtils.hasText(uf) && !StringUtils.hasText(localidade)) {
			return repository.findByAddresses_Uf(uf, pageable);
		} else if (!StringUtils.hasText(uf) && StringUtils.hasText(localidade)) {
			return repository.findByAddresses_Localidade(localidade, pageable);
		} else {
			return repository.findAll(pageable);
		}

	}

}
