package com.eca.visitor.service.impl;

import com.eca.visitor.constants.VisitorConstants;
import com.eca.visitor.dto.DataItem;
import com.eca.visitor.dto.UserDTO;
import com.eca.visitor.dto.UserRespDTO;
import com.eca.visitor.dto.VisitorMessageDTO;
import com.eca.visitor.dto.VisitorRegistrationRequest;
import com.eca.visitor.dto.response.VisitorRegistrationResponse;
import com.eca.visitor.dto.response.VisitorResponse;
import com.eca.visitor.entity.Visitor;
import com.eca.visitor.exception.ExternalApiException;
import com.eca.visitor.exception.VisitorManagementException;
import com.eca.visitor.repository.VisitorRepository;
import com.eca.visitor.service.VisitorRegistrationService;
import com.eca.visitor.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class VisitorRegistrationServiceImpl extends ExternalAPICall implements VisitorRegistrationService {

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CommonUtils commonUtils;

    @Value("${app.visitor.kafka.enabled}")
    private boolean kafkaEnabled;

    @Override
    public ResponseEntity<VisitorRegistrationResponse> visitorRegistration(VisitorRegistrationRequest requestDto) {
        var visitorEntity = modelMapper.map(requestDto, Visitor.class);
        var userPhoneNo = visitorEntity.getUserPhoneNo();
        ResponseEntity<UserRespDTO> userResponse = getPhoneNumber(userPhoneNo);
        log.info("VisitorRegistrationServiceImpl::visitorRegistration::getPhoneNumber response {} ",userResponse);
        if (userResponse.getStatusCode().equals(HttpStatus.SERVICE_UNAVAILABLE)) {
           throw new ExternalApiException("ECA User-Management Service Not Available");
        }
        var visitorToSave = parseUserDetailsForvisitor(visitorEntity,getUserDetails(userResponse,userPhoneNo));
        return Optional.of(visitorRepository.save(visitorToSave))
                .map(visitor -> {
                    if (kafkaEnabled) {
                        sendVisitorNotification(visitorToSave);
                    }
                    return createVisitorRegistrationResponse(visitorToSave);
                }).orElseThrow(() -> new VisitorManagementException("Unable To Save Visitor Data "));
    }

    private void sendVisitorNotification(Visitor visitor) {
        VisitorMessageDTO visitorKafkaMessageDto = commonUtils.createVisitorMessageDto(visitor);
        commonUtils.pushNotification(visitorKafkaMessageDto);
    }
    private Visitor parseUserDetailsForvisitor(Visitor visitorEntity, UserDTO userDto) {
        visitorEntity.setUserFirstName(userDto.getUserFirstName());
        visitorEntity.setUserLastName(userDto.getUserLastName());
        visitorEntity.setApartmentId(userDto.getApartmentId());
        visitorEntity.setApartmentName(userDto.getApartmentName());
        visitorEntity.setUserEmailId(userDto.getUserEmailId());
        visitorEntity.setVisitorRequestId("VREQ-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        return visitorEntity;
    }

    private UserDTO getUserDetails(ResponseEntity<UserRespDTO> response, Long phoneNUmber) {
        return Optional.ofNullable(response.getBody())
                .map(UserRespDTO::getData)
                .flatMap(data -> data.stream().findFirst())
                .map(this::parseUserResponse)
                .orElseThrow(() -> new VisitorManagementException("User is not available with phone number : " + phoneNUmber));
    }

    private UserDTO parseUserResponse(DataItem userdata) {
        return Optional.ofNullable(userdata)
                .map(dataItem -> {
                    if (userdata.getOwner() != null) {
                        return commonUtils.parseOwnerDetails(userdata.getOwner());
                    } else if (userdata.getVendor() != null) {
                        return commonUtils.parseVendorDetails(userdata.getVendor());
                    } else {
                        return commonUtils.parseTenantDetails(userdata.getTenant());
                    }
                })
                .orElseThrow(() -> new VisitorManagementException(VisitorConstants.USER_DETAILS_FOR_VISITOR_NOT_FOUND));
    }

    private ResponseEntity<VisitorRegistrationResponse> createVisitorRegistrationResponse (Visitor visitor){
        var visitorResponse = modelMapper.map(visitor, VisitorResponse.class);
        var visitorRegistrationResponse = new VisitorRegistrationResponse(visitorResponse, LocalDateTime.now());
        return new ResponseEntity<>(visitorRegistrationResponse, HttpStatus.CREATED);
    }

}

