package com.eca.visitor.service.impl;

import com.eca.visitor.dto.UserRespDTO;
import com.eca.visitor.service.impl.ExternalAPICall;
import com.eca.visitor.service.impl.VisitorRegistrationServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ExternalAPITest {

	@Test
	void testFallBack() {
		class TestExternal extends ExternalAPICall {}
		ResponseEntity<UserRespDTO> ok = new TestExternal().fallback(new RuntimeException("OK"));
		Assertions.assertThat(ok).isNotNull();
		Assertions.assertThat(ok.getStatusCode())
				.isEqualTo(HttpStatus.SERVICE_UNAVAILABLE);
	}
}
