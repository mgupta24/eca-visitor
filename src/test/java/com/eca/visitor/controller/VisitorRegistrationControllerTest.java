package com.eca.visitor.controller;

import com.eca.visitor.dto.UserRespDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource("classpath:application-test.yaml")
@ActiveProfiles("test")
@Execution(ExecutionMode.SAME_THREAD)
public class VisitorRegistrationControllerTest {

    public static final String REG_URL_PATH = "/v1/visitor/registration";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RestTemplate restTemplate;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @SneakyThrows
    @Test
    void checkVisitorRegistrationForOwner() {
        String ownerJson = IOUtils.toString(Objects.requireNonNull(this.getClass().getResourceAsStream("/ownerUserResponse.json")),
                StandardCharsets.UTF_8);
        var ownerResponse = OBJECT_MAPPER.readValue(ownerJson, UserRespDTO.class);
        when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(), Mockito.eq(UserRespDTO.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(ownerResponse));
        var json = IOUtils.toString(Objects.requireNonNull(this.getClass().getResourceAsStream("/visitorRegistrationRequest.json")),
                StandardCharsets.UTF_8);
        mvc.perform(MockMvcRequestBuilders
                        .post(REG_URL_PATH)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.visitorId").exists())
                .andExpect(jsonPath("$.data.apartmentId").exists())
              .andExpect(jsonPath("$.data.visitorFirstName",is(equalTo("TestVisitor"))));
    }


    private void mockRestTemplate(RestTemplate restTemplateMock, ResponseEntity<UserRespDTO> responseEntityMock) {
        Mockito.when(restTemplate.exchange(
                Mockito.anyString(),  // URL
                Mockito.eq(HttpMethod.GET),  // HTTP method
                Mockito.any(),  // Request entity
                Mockito.eq(UserRespDTO.class)  // Response type
        )).thenReturn(responseEntityMock);
        restTemplate = restTemplateMock;
    }

    @SneakyThrows
    @Test
    void checkVisitorRegistrationForTenant() {
        var tenantJson = IOUtils.toString(Objects.requireNonNull(this.getClass().getResourceAsStream(
                "/tenantUserResponse.json")),
                StandardCharsets.UTF_8);
        var tenantResponse = OBJECT_MAPPER.readValue(tenantJson, UserRespDTO.class);
        when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(), Mockito.eq(UserRespDTO.class)))
                .thenReturn( ResponseEntity.status(HttpStatus.OK).body(tenantResponse));
        var json = IOUtils.toString(Objects.requireNonNull(this.getClass().getResourceAsStream("/visitorRegistrationRequest.json")),
                StandardCharsets.UTF_8);
        mvc.perform(MockMvcRequestBuilders
                        .post(REG_URL_PATH)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.visitorId").exists())
                .andExpect(jsonPath("$.data.apartmentId").exists())
                .andExpect(jsonPath("$.data.visitorFirstName",is(equalTo("TestVisitor"))));
    }

    @SneakyThrows
    @Test
    void checkVisitorRegistrationForVendor() {
        String vendorJson = IOUtils.toString(Objects.requireNonNull(this.getClass().getResourceAsStream("/vendorUserResponse.json")),
                StandardCharsets.UTF_8);
        var vendorResponse = OBJECT_MAPPER.readValue(vendorJson, UserRespDTO.class);
        when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(), Mockito.eq(UserRespDTO.class)))
                .thenReturn( ResponseEntity.status(HttpStatus.OK).body(vendorResponse));
        var json = IOUtils.toString(Objects.requireNonNull(this.getClass().getResourceAsStream("/visitorRegistrationRequest.json")),
                StandardCharsets.UTF_8);
        mvc.perform(MockMvcRequestBuilders
                        .post(REG_URL_PATH)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.visitorId").exists())
                .andExpect(jsonPath("$.data.apartmentId").exists())
                .andExpect(jsonPath("$.data.visitorFirstName",is(equalTo("TestVisitor"))));
    }

   @Test
    void checkVisitorInvalidRegistration() throws Exception {
        var json = IOUtils.toString(Objects.requireNonNull(this.getClass().getResourceAsStream("/invalidVisitorRegistrationRequest.json")),
                StandardCharsets.UTF_8);
        mvc.perform(MockMvcRequestBuilders
                        .post(REG_URL_PATH)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", is(notNullValue())));
    }

    @SneakyThrows
    @Test
    void checkFallBackNonFoundTest() {
        when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(), Mockito.eq(UserRespDTO.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        var json = IOUtils.toString(Objects.requireNonNull(this.getClass().getResourceAsStream("/visitorRegistrationRequest.json")),
                StandardCharsets.UTF_8);
        mvc.perform(MockMvcRequestBuilders
                        .post(REG_URL_PATH)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @SneakyThrows
    @Test
    void checkFallBackServiceNotAvailableTest() {
        when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(), Mockito.eq(UserRespDTO.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
        var json = IOUtils.toString(Objects.requireNonNull(this.getClass().getResourceAsStream("/visitorRegistrationRequest.json")),
                StandardCharsets.UTF_8);
        mvc.perform(MockMvcRequestBuilders
                        .post(REG_URL_PATH)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isServiceUnavailable());
    }

}
