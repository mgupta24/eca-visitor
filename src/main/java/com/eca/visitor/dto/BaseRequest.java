package com.eca.visitor.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class BaseRequest {
	
	@NotEmpty
	protected String visitorFirstName;

	protected String visitorLastName;

	protected String visitorAddressLine;

	protected String visitorCity;

	protected String visitorState;

	protected String visitorZipCode;

	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	@NotNull
	protected Long userPhoneNo;
}
