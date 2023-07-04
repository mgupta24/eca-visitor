package com.eca.visitor.utils;

import com.eca.visitor.dto.OwnerDTO;
import com.eca.visitor.dto.TenantDTO;
import com.eca.visitor.dto.UserDTO;
import com.eca.visitor.dto.VendorDTO;
import com.eca.visitor.dto.VisitorMessageDTO;
import com.eca.visitor.entity.Visitor;
import com.eca.visitor.service.notification.VisitorNotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonUtils {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired(required = false)
    private VisitorNotificationService visitorKafkaNotificationService;

    @Autowired
    private JsonUtils jsonUtils;

    @Autowired
    private ModelMapper modelMapper;

    public UserDTO parseOwnerDetails(OwnerDTO owner) {

        UserDTO userDto = new UserDTO();
        userDto.setUserFirstName(owner.getFirstName());
        userDto.setUserLastName(owner.getLastName());
        userDto.setApartmentId(owner.getApartmentId());
        userDto.setApartmentName(owner.getApartmentName());
        userDto.setUserEmailId(owner.getEmailId());
        return userDto;
    }

    public UserDTO parseVendorDetails(VendorDTO vendorDTO) {

        UserDTO userDto = new UserDTO();
        userDto.setUserFirstName(vendorDTO.getFirstName());
        userDto.setUserLastName(vendorDTO.getLastName());
        userDto.setApartmentId(vendorDTO.getApartmentId());
        userDto.setApartmentName(vendorDTO.getApartmentName());
        userDto.setUserEmailId(vendorDTO.getEmailId());
        return userDto;
    }

    public UserDTO parseTenantDetails(TenantDTO tenantDTO) {

        UserDTO userDto = new UserDTO();
        userDto.setUserFirstName(tenantDTO.getFirstName());
        userDto.setUserLastName(tenantDTO.getLastName());
        userDto.setApartmentId(tenantDTO.getApartmentId());
        userDto.setApartmentName(tenantDTO.getApartmentName());
        userDto.setUserEmailId(tenantDTO.getEmailId());
        return userDto;
    }

    public void pushNotification(VisitorMessageDTO visitorKafkaMessageDTO) {
        visitorKafkaNotificationService.sendNotification(jsonUtils.toJson(visitorKafkaMessageDTO));

    }
    public VisitorMessageDTO createVisitorMessageDto(Visitor visitor) {
        VisitorMessageDTO visitorKafkaMessageDTO = modelMapper.map(visitor, VisitorMessageDTO.class);
        visitorKafkaMessageDTO.setUserFirstName(visitor.getVisitorFirstName());
        visitorKafkaMessageDTO.setUserLastName(visitor.getVisitorLastName());
        return visitorKafkaMessageDTO;
    }



}
