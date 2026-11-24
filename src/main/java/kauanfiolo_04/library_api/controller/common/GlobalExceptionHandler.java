package kauanfiolo_04.library_api.controller.common;

import kauanfiolo_04.library_api.controller.dto.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
    public ResponseError handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();

        List<kauanfiolo_04.library_api.controller.dto.FieldError> errorsList = fieldErrors
            .stream()
            .map(f -> new kauanfiolo_04.library_api.controller.dto.FieldError(
                f.getField(), f.getDefaultMessage()
            ))
            .collect(Collectors.toList());

        return new ResponseError(
            HttpStatus.UNPROCESSABLE_CONTENT.value(),
            "Validation error.",
            errorsList
        );
    }
}
