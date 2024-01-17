package com.example.api.domain.enums;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.api.exceptions.BusinessException;

public enum GenderEnum {
	M, F;

	public static GenderEnum fromString(String value) {
		return Stream.of(values()).filter(g -> g.name().equalsIgnoreCase(value)).findFirst()
				.orElseThrow(() -> new BusinessException("Gender is invalid!"));
	}

	public static boolean isValid(String value) {
		return Stream.of(values()).filter(g -> g.name().equalsIgnoreCase(value)).findFirst().isPresent();
	}

	public static String allowValues() {
		return String.join(", ", Stream.of(GenderEnum.values()).map(GenderEnum::name).collect(Collectors.toList()));
	}

}
