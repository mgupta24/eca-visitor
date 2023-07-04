package com.eca.visitor.controller;

import com.eca.visitor.dto.VisitorRegistrationRequest;
import com.eca.visitor.dto.response.VisitorRegistrationResponse;
import com.eca.visitor.service.VisitorRegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/v1/visitor")
@Slf4j
public class VisitorRegistrationController {

    @Autowired
    private VisitorRegistrationService visitorRegistrationService;

    @Autowired
    private Validator validator;

    @PostMapping(path="registration",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VisitorRegistrationResponse> registration(@RequestBody VisitorRegistrationRequest visitorRegistrationrequest) {
        log.info("VisitorRegistrationController::registration request from UI {}",visitorRegistrationrequest);
        Set<ConstraintViolation<VisitorRegistrationRequest>> constraintViolations = validator.validate(visitorRegistrationrequest);
        if (!constraintViolations.isEmpty()) {
            log.error("VisitorRegistrationController::registration constraintViolations errors list {}",constraintViolations);
            throw new ConstraintViolationException(constraintViolations);
        }
        return visitorRegistrationService.visitorRegistration(visitorRegistrationrequest);

    }

    @GetMapping("/checkApprovalStatus/{requestId}")
    public ResponseEntity<String> checkApprovalStatus(@PathVariable String requestId)  {
        log.info("Checking Visitor Approval Status");
        return ResponseEntity.ok("visitor request has been approved");
    }

}
