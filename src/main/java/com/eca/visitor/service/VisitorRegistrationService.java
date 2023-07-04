package com.eca.visitor.service;

import com.eca.visitor.dto.VisitorRegistrationRequest;
import com.eca.visitor.dto.response.VisitorRegistrationResponse;
import org.springframework.http.ResponseEntity;

public interface VisitorRegistrationService {

    ResponseEntity<VisitorRegistrationResponse> visitorRegistration(VisitorRegistrationRequest visitorDto) ;
}
