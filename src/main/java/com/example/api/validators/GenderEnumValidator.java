package com.example.api.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.api.domain.enums.GenderEnum;

public class GenderEnumValidator implements ConstraintValidator<Gender, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return GenderEnum.isValid(value);
	}

}

