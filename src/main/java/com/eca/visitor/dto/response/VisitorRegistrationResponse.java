package com.eca.visitor.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class VisitorRegistrationResponse {

	public VisitorRegistrationResponse(VisitorResponse visitorResponse, LocalDateTime now) {
		this.visitorResponse = visitorResponse;
		this.timestamp = now;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	protected LocalDateTime timestamp;

	protected String error;
	@JsonProperty("data")
	private VisitorResponse visitorResponse;

	private String status;


}
