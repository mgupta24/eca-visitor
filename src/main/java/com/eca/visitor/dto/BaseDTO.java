package com.eca.visitor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BaseDTO {

	@JsonProperty("type")
	protected String type;

	@JsonProperty("firstName")
	protected String firstName;

	@JsonProperty("lastName")
	protected String lastName;

	@JsonProperty("phoneNo")
	protected long phoneNo;

	@JsonProperty("emailId")
	protected String emailId;

	@JsonProperty("addressLine")
	protected String addressLine;

	@JsonProperty("city")
	protected String city;

	@JsonProperty("state")
	protected String state;

	@JsonProperty("zipCode")
	protected String zipCode;

	@JsonProperty("apartmentId")
	protected int apartmentId;

	@JsonProperty("apartmentName")
	protected String apartmentName;

	@JsonProperty("id")
	protected int id;
}
