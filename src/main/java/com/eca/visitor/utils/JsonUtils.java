package com.eca.visitor.utils;

import com.eca.visitor.constants.VisitorConstants;
import com.eca.visitor.exception.VisitorManagementException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JsonUtils {
    @Autowired
    private ObjectMapper objectMapper;

    public <T> String toJson(T t) {
        try {
            return objectMapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new VisitorManagementException(VisitorConstants.JSON_PROCESSING_ERROR,e);
        }
    }

}
