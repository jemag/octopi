package ca.ogsl.octopi.exception;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Value;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Value
@JsonPropertyOrder({"timestamp", "status", "error", "message", "path"})
public class APIException {
  private final Instant timestamp;
  private final HttpStatus status;
  private final List<String> errors;
  private final String message;
  private final String path;
  
  public APIException(Instant timestamp, HttpStatus status, List<String> errors,
                      String message, String path) {
    this.timestamp = timestamp;
    this.status = status;
    this.errors = errors;
    this.message = message;
    this.path = path;
  }
  
  public APIException(Instant timestamp, HttpStatus status, String error,
                      String message, String path) {
    this.timestamp = timestamp;
    this.status = status;
    this.errors = Collections.singletonList(error);
    this.message = message;
    this.path = path;
  }
}
