package ca.ogsl.octopi.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
public class APIValidationException extends RuntimeException {
  Collection<APIValidationError> apiValidationErrors;
  
  public APIValidationException() {
  }
  
  public APIValidationException(String message) {
    super(message);
  }
  
  public APIValidationException(String message,
                                Collection<APIValidationError> apiValidationErrors) {
    super(message);
    this.apiValidationErrors = apiValidationErrors;
  }
}
