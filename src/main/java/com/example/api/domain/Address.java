package com.example.api.domain;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.example.api.domain.enums.GenderEnum;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CUSTOMER")
@Getter
@Setter
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	@NotEmpty
	@Email
	private String email;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private GenderEnum gender;

}
