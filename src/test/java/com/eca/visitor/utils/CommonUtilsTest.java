package com.eca.visitor.utils;

import com.eca.visitor.dto.VisitorMessageDTO;
import com.eca.visitor.entity.Visitor;
import com.eca.visitor.service.notification.VisitorNotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CommonUtilsTest {
	@Mock
	private ObjectMapper objectMapper;

	@Mock
	private VisitorNotificationService visitorKafkaNotificationService;

	@Mock
	private JsonUtils jsonUtils;

	@InjectMocks
	private ModelMapper modelMapper;

	@InjectMocks
	private CommonUtils commonUtils;

	@BeforeEach
	void init() {
		ReflectionTestUtils.setField(commonUtils,"modelMapper",modelMapper);
	}

	@Test
	void createVisitorTest(){
		var visitor = Visitor.builder().visitorFirstName("Test")
				.visitorLastName("Gupta").build();
		assertThat(commonUtils.createVisitorMessageDto(visitor))
				.isNotNull()
				.extracting(VisitorMessageDTO::getVisitorFirstName, VisitorMessageDTO::getVisitorLastName)
				.doesNotContainNull()
				.contains("Test","Gupta");
	}
}
