package com.ewallet.handler;

import com.ewallet.exception.*;
import com.ewallet.model.entity.ServiceResponse;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<ServiceResponse> internalServerError(Exception ex, WebRequest request) {

		ServiceResponse serviceResponse = new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
			ex.getMessage());

		return new ResponseEntity<>(serviceResponse, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler({ CustomerDoesNotExistException.class, WalletIdDoesNotExistException.class })
	public ResponseEntity<ServiceResponse> notFound(Exception ex, WebRequest request) {

		ServiceResponse serviceResponse = new ServiceResponse(HttpStatus.NOT_FOUND.toString(),
			ex.getMessage());

		return new ResponseEntity<>(serviceResponse, HttpStatus.NOT_FOUND);

	}
}
