package com.minegocio.app.errors;

import com.minegocio.app.dtos.error.ErrorDto;
import com.minegocio.app.errors.exceptions.AlreadyExistsException;
import com.minegocio.app.errors.exceptions.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class WebExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
        ErrorDto error = ErrorDto
                .builder()
                .messages(Collections.singletonList(ex.getMessage()))
                .resource(request.getRequestURI())
                .build();
        return buildResponseEntity(NOT_FOUND, error);
    }

    @ExceptionHandler({AlreadyExistsException.class})
    public ResponseEntity<?> handleAlreadyExistsException(AlreadyExistsException ex, HttpServletRequest request) {
        ErrorDto error = ErrorDto
                .builder()
                .messages(Collections.singletonList(ex.getMessage()))
                .resource(request.getRequestURI())
                .build();
        return buildResponseEntity(CONFLICT, error);
    }

    @Override
    protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errorMessages = new ArrayList<>();
        errorMessages.addAll(ex.getBindingResult().getFieldErrors().stream().map(this::serialize).collect(Collectors.toList()));
        ErrorDto error = ErrorDto
                .builder()
                .messages(errorMessages)
                .resource(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();
        return buildResponseEntity(BAD_REQUEST, error);
    }

    private ResponseEntity buildResponseEntity(HttpStatus httpStatus, ErrorDto error) {
        return new ResponseEntity(error, httpStatus);
    }

    private String serialize(FieldError fieldError) {
        return format("{0}.{1}: {2}", fieldError.getObjectName(), fieldError.getField(), StringUtils.capitalize(fieldError.getDefaultMessage()));
    }

}
