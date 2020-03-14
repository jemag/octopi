package ca.ogsl.octopi.exception.controller;

import ca.ogsl.octopi.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("NullableProblems")
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
  
  private final String CONTACT_EMAIL = "CHSINFO.XNCR@dfo-mpo.gc.ca";
  private final String REPORT_ERROR_MESSAGE =
      "If you wish to report this error, contact us at " + CONTACT_EMAIL;
  
  @ExceptionHandler({MethodArgumentTypeMismatchException.class})
  public ResponseEntity<APIException> handleMethodArgumentTypeMismatch(
      final MethodArgumentTypeMismatchException ex, final WebRequest request) {
    final String error = ex.getName() + " is improperly formatted";
    final APIException apiException = new APIException(Instant.now(),
        HttpStatus.BAD_REQUEST, error,
        "Wrong parameter format", ((ServletWebRequest) request).getRequest().getRequestURI());
    
    return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.getStatus());
  }
  
  @ExceptionHandler({ConstraintViolationException.class})
  public ResponseEntity<APIException> handleConstraintViolation(
      final ConstraintViolationException ex,
      final WebRequest request) {
    final List<String> errors = new ArrayList<>();
    for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
      errors.add(violation.getInvalidValue() + ": "
          + violation.getMessage());
    }
    final APIException apiException = new APIException(Instant.now(),
        HttpStatus.BAD_REQUEST, errors,
        "Constraints not respected", ((ServletWebRequest) request).getRequest().getRequestURI());
    
    return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.getStatus());
  }
  
  @ExceptionHandler({APIValidationException.class})
  public ResponseEntity<APIException> handleAPIValidationError(final APIValidationException ex,
                                                               final WebRequest request) {
    final List<String> errors = new ArrayList<>();
    for (final APIValidationError validationError : ex.getApiValidationErrors()) {
      errors.add(validationError.getInvalidValue() + ": " + validationError.getErrorMessage());
    }
    final APIException apiException = new APIException(Instant.now(),
        HttpStatus.BAD_REQUEST, errors,
        "Constraints not respected", ((ServletWebRequest) request).getRequest().getRequestURI());
    
    return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.getStatus());
  }
  
  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
      final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers,
      final HttpStatus status, final WebRequest request) {
    final StringBuilder builder = new StringBuilder();
    builder.append(ex.getMethod());
    builder.append(" method is not supported for this request. Supported methods are ");
    ex.getSupportedHttpMethods().forEach(t -> builder.append(t).append(" "));
    final APIException apiException = new APIException(Instant.now(),
        HttpStatus.METHOD_NOT_ALLOWED, builder.toString(),
        "Request method not supported", ((ServletWebRequest) request).getRequest().getRequestURI());
    
    return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.getStatus());
  }
  
  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
      final HttpMediaTypeNotSupportedException ex, final HttpHeaders headers,
      final HttpStatus status, final WebRequest request) {
    final StringBuilder builder = new StringBuilder();
    builder.append(ex.getContentType());
    builder.append(" media type is not supported. Supported media types are ");
    ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(" "));
    final APIException apiException = new APIException(Instant.now(),
        HttpStatus.UNSUPPORTED_MEDIA_TYPE, builder.substring(0, builder.length() - 2),
        "Media not supported", ((ServletWebRequest) request).getRequest().getRequestURI());
    
    return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.getStatus());
  }
  
  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      final MissingServletRequestParameterException ex, final HttpHeaders headers,
      final HttpStatus status, final WebRequest request) {
    final String error = ex.getParameterName() + " parameter is missing";
    final APIException apiException = new APIException(Instant.now(),
        HttpStatus.BAD_REQUEST, error,
        "Missing parameter", ((ServletWebRequest) request).getRequest().getRequestURI());
    return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.getStatus());
  }
  
  @Override
  protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex,
                                                      final HttpHeaders headers,
                                                      final HttpStatus status,
                                                      final WebRequest request) {
    final String error = ex.getValue() + " value for " + ex.getPropertyName() + " is of wrong " +
        "type";
    final APIException apiException = new APIException(Instant.now(),
        HttpStatus.BAD_REQUEST, error,
        ex.getLocalizedMessage(), ((ServletWebRequest) request).getRequest().getRequestURI());
    return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.getStatus());
  }
  
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status,
      final WebRequest request) {
    List<String> errors = getErrorsFromBindingResults(ex.getBindingResult());
    final APIException apiException = new APIException(Instant.now(),
        HttpStatus.BAD_REQUEST, errors,
        ex.getLocalizedMessage(), ((ServletWebRequest) request).getRequest().getRequestURI());
    return handleExceptionInternal(ex, apiException, headers, apiException.getStatus(), request);
  }
  
  private List<String> getErrorsFromBindingResults(BindingResult bindingResult) {
    final List<String> errors = new ArrayList<>();
    if (Objects.nonNull(bindingResult)) {
      for (final FieldError error : bindingResult.getFieldErrors()) {
        errors.add(error.getField() + ": " + error.getDefaultMessage());
      }
      for (final ObjectError error : bindingResult.getGlobalErrors()) {
        errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
      }
    }
    return errors;
  }
  
  @Override
  protected ResponseEntity<Object> handleMissingServletRequestPart(
      final MissingServletRequestPartException ex, final HttpHeaders headers,
      final HttpStatus status, final WebRequest request) {
    final String error = ex.getRequestPartName() + " part is missing";
    final APIException apiException = new APIException(Instant.now(),
        HttpStatus.BAD_REQUEST, error,
        "Missing request part", ((ServletWebRequest) request).getRequest().getRequestURI());
    return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.getStatus());
  }
  
  @Override
  protected ResponseEntity<Object> handleBindException(final BindException ex,
                                                       final HttpHeaders headers,
                                                       final HttpStatus status,
                                                       final WebRequest request) {
    List<String> errors = getErrorsFromBindingResults(ex.getBindingResult());
    
    final APIException apiException = new APIException(Instant.now(),
        HttpStatus.BAD_REQUEST, errors,
        "Invalid parameters", ((ServletWebRequest) request).getRequest().getRequestURI());
    return handleExceptionInternal(ex, apiException, headers, apiException.getStatus(), request);
  }
  
  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex,
                                                                 final HttpHeaders headers,
                                                                 final HttpStatus status,
                                                                 final WebRequest request) {
    final String error = "Invalid API endpoint: " + ex.getHttpMethod() + " " + ex.getRequestURL();
    final APIException apiException = new APIException(Instant.now(),
        HttpStatus.NOT_FOUND, error,
        ex.getLocalizedMessage(), ((ServletWebRequest) request).getRequest().getRequestURI());
    return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.getStatus());
  }
  
  @ExceptionHandler({Exception.class})
  public ResponseEntity<APIException> exceptionHandler(final Exception ex,
                                                       final WebRequest request) {
    log.error("unhandled exception", ex);
    final APIException apiException = new APIException(Instant.now(),
        HttpStatus.INTERNAL_SERVER_ERROR, REPORT_ERROR_MESSAGE,
        "internal error", ((ServletWebRequest) request).getRequest().getRequestURI());
    return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.getStatus());
  }
  
  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<APIException> entityNotFoundExceptionHandler(
      EntityNotFoundException entityNotFoundException,
      WebRequest request) {
    APIException apiException = new APIException(Instant.now(), HttpStatus.NOT_FOUND,
        Collections.singletonList("Not Found"),
        entityNotFoundException.getMessage(),
        ((ServletWebRequest) request).getRequest().getRequestURI());
    return new ResponseEntity<>(apiException, new HttpHeaders(),
        apiException.getStatus());
  }
  
  @ExceptionHandler(InvalidRequestException.class)
  public ResponseEntity<APIException> invalidRequestExceptionHandler(
      InvalidRequestException invalidRequestException, WebRequest request) {
    APIException apiException = new APIException(Instant.now(), HttpStatus.BAD_REQUEST,
        Collections.singletonList("Bad Request"),
        invalidRequestException.getMessage(),
        ((ServletWebRequest) request).getRequest().getRequestURI());
    return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.getStatus());
  }
}
