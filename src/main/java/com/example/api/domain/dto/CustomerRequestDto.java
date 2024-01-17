package com.example.api.domain.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.example.api.validators.ValidGenderEnum;

import lombok.Data;

@Data
public class CustomerRequestDto {

	@NotBlank()
	@Size(min = 2)
	private String name;

	@ValidGenderEnum
	private String gender;

	@NotBlank
	@Email
	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
