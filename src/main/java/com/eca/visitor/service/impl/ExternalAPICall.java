package com.eca.visitor.service.impl;

import com.eca.visitor.dto.UserRespDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
public abstract class ExternalAPICall {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${microservice.user-service.endpoints.endpoint.uri}")
	private String endpointUrl;

	@Value("${app.user-mgmt.authTkn}")
	private String jwtToken;

	@Autowired
	private CircuitBreakerFactory circuitBreakerFactory;

	public ResponseEntity<UserRespDTO> getPhoneNumber(Long userPhoneNo) {
		var entity = new HttpEntity<>(getHttpHeaders());
		var uri = UriComponentsBuilder.fromHttpUrl(endpointUrl)
				.buildAndExpand(userPhoneNo)
				.toUriString();
		log.info("ExternalAPICall request  {} ",uri);
		CircuitBreaker circuitbreaker = circuitBreakerFactory.create("circuitbreaker");
		return circuitbreaker.run(() ->restTemplate.exchange(uri, HttpMethod.GET, entity, UserRespDTO.class),
				this::fallback);
	}

	protected HttpHeaders getHttpHeaders() {
		var headers = new HttpHeaders();
		headers.set("Authorization", StringUtils.join("Bearer ", jwtToken));
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

	public ResponseEntity<UserRespDTO> fallback(Throwable throwable) {
		log.error("Fallback Error {} With Message {}  ",throwable, throwable.getMessage());
		if(throwable instanceof  HttpClientErrorException && StringUtils.isNotBlank(throwable.getMessage())) {
			HttpClientErrorException httpClientErrorException = (HttpClientErrorException) throwable;
			return ResponseEntity.status(httpClientErrorException.getStatusCode()).build();
		}
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
	}
}
