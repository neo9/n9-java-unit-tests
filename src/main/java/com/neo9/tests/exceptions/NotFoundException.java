package com.neo9.tests.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = true)
public class NotFoundException extends RuntimeException {
	public final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

	public NotFoundException() {
		super("Resource not found");
	}

	public NotFoundException(String message) {
		super(message);
	}
}
