package com.eca.visitor.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisitorResponse {

    private String visitorFirstName;
    private String visitorLastName;
    private long visitorId;
    private String visitorRequestId;
    private String visitorAddressLine;
    private String visitorCity;
    private String visitorState;
    private String visitorZipCode;
    private String purposeOfVisiting;
    private String userFirstName;
    private String userLastName;
    private String userEmailId;
    private Long userPhoneNo;
    private int apartmentId;
    private String apartmentName;


}
