package com.example.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

import com.example.api.domain.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

	Page<Customer> findAll(Pageable pageable);

	Page<Customer> findAll(Specification<Customer> specification, Pageable pageable);

	Page<Customer> findByAddresses_UfAndAddresses_Localidade(String uf, String localidade, Pageable pageable);

	Page<Customer> findByAddresses_Uf(String uf, Pageable pageable);

	Page<Customer> findByAddresses_Localidade(String localidade, Pageable pageable);

}
