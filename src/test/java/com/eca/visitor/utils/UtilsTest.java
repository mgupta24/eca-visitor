package com.eca.visitor.utils;

import com.eca.visitor.exception.VisitorManagementException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class UtilsTest {

	@InjectMocks
	private JsonUtils jsonUtils;

	@Mock
	private ObjectMapper objectMapper;
	@Test
	@SneakyThrows
	void testToJson() {
		class JsonMockException extends JsonProcessingException {
			protected JsonMockException(String msg) {
				super(msg);
			}
		}
		Mockito.when(objectMapper.writeValueAsString(Mockito.anyString())).thenThrow(new JsonMockException("ERROR"));
		Assertions.assertThatThrownBy(() -> jsonUtils.toJson("{"))
				.isInstanceOf(VisitorManagementException.class);
	}

	@Test
	@SneakyThrows
	void testValidToJson() {
		Mockito.when(objectMapper.writeValueAsString(Mockito.anyString())).thenReturn("{}");
		Assertions.assertThat(jsonUtils.toJson("JAIDEV")).isNotBlank();
	}
}
