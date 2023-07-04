package com.eca.visitor.advice;

import com.eca.visitor.constants.VisitorConstants;
import com.eca.visitor.dto.response.ErrorResponse;
import com.eca.visitor.exception.ExternalApiException;
import com.eca.visitor.exception.VisitorManagementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.time.LocalDateTime;

@ControllerAdvice(annotations = RestController.class)
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


	@ExceptionHandler({SQLException.class,HttpClientErrorException.class,VisitorManagementException.class,
			ConstraintViolationException.class})
	public ResponseEntity<ErrorResponse> httpBadRequest(Exception ex) {
		log.error(VisitorConstants.GLOBAL_EXCEPTION_HANDLER_KEY,ex.getMessage());
		return new ResponseEntity<>(createErrorResponse(ex), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({Exception.class})
	public ResponseEntity<ErrorResponse> internalError(Exception ex) {
		log.error("GlobalExceptionHandler internalError {}",ex.getMessage());
		return new ResponseEntity<>(createErrorResponse(ex), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ExternalApiException.class})
	public ResponseEntity<ErrorResponse> serviceNonAvailable(Exception ex) {
		log.error("GlobalExceptionHandler serviceNonAvailable {}",ex.getMessage());
		return new ResponseEntity<>(createErrorResponse(ex), HttpStatus.SERVICE_UNAVAILABLE);
	}


	private ErrorResponse createErrorResponse(Exception exception) {
		var errorResponse = new ErrorResponse();
		errorResponse.setTimestamp(LocalDateTime.now());
		errorResponse.setError(exception.getMessage());
		return errorResponse;
	}

}
