package com.example.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ADDRESS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String cep;

	private String logradouro;

	private String complemento;

	private String bairro;

	@Column(nullable = false)
	private String localidade;

	@Column(nullable = false)
	private String uf;

	@Column(nullable = false)
	private String ibge;

	private String gia;

	@Column(nullable = false)
	private String ddd;

	@Column(nullable = false)
	private String siafi;

}
