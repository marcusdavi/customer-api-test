package com.example.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.api.domain.service.cep.dto.AddressCepResponseDTO;

@Service
public class CepService {

	private RestTemplate restTemplate;

	@Autowired
	public CepService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Value("${cep.service.url.base}")
	private String baseURL;

	public Optional<AddressCepResponseDTO> getAddress(Integer cep) {

		String cepFull = String.format("%08d", cep);
		ResponseEntity<AddressCepResponseDTO> response = restTemplate.exchange(String.format(baseURL, cepFull),
				HttpMethod.GET, null, new ParameterizedTypeReference<AddressCepResponseDTO>() {
				});

		if (response.getBody() == null || response.getBody().isErro()) {
			return Optional.empty();
		} else {
			return Optional.of(response.getBody());
		}
	}

}
