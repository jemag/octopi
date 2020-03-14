package ca.ogsl.octopi.exception;

import lombok.Value;

@Value
public class APIValidationError {
  private String invalidValue;
  private String errorMessage;
}
