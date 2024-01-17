package com.example.api.domain.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.example.api.validators.ValidGenderEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequestDto {

	@NotBlank()
	@Size(min = 2)
	private String name;

	@ValidGenderEnum
	private String gender;

	@NotBlank
	@Email
	private String email;


}
