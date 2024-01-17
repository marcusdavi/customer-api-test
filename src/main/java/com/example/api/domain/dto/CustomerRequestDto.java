package com.example.api.domain.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.example.api.validators.Gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CustomerRequestDto {

	@NotBlank()
	@Size(min = 2)
	private String name;

	@Gender
	private String gender;

	@NotBlank
	@Email
	private String email;


}
