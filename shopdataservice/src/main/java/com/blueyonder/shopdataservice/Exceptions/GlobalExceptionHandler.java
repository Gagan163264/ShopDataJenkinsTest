package com.blueyonder.shopdataservice.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Response{
        private String message;
    }

    private ResponseEntity<Object> makeResponse(ErrorObject obj){
        return new ResponseEntity<>(new Response(obj.getMessage()),obj.getStatus());
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Object> handleCategoryNotFoundException(Exception ex){
        ErrorObject errorObj = new ErrorObject(HttpStatus.NOT_FOUND, ex);
        return makeResponse(errorObj);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(Exception ex){
        ErrorObject errorObj = new ErrorObject(HttpStatus.NOT_FOUND, ex);
        return makeResponse(errorObj);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<Object> handleNFException(Exception ex){
        ErrorObject errorObject = new ErrorObject(HttpStatus.BAD_REQUEST, ex);
        return new ResponseEntity<>(new Response(errorObject.getResponse(ErrorMessages.NUMBER_FORMAT, "")), errorObject.getStatus());
    }

    @ExceptionHandler(TooManyCharactersException.class)
    public ResponseEntity<Object> handleDataException(Exception ex){
        ErrorObject errorObject = new ErrorObject(HttpStatus.BAD_REQUEST, ex);
        return makeResponse(errorObject);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHTTPParseError(Exception ex){
        ErrorObject errorObject = new ErrorObject(HttpStatus.BAD_REQUEST, ex);
        return makeResponse(errorObject);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleMissingParams(MissingServletRequestParameterException ex) {
        ErrorObject errorObject = new ErrorObject(HttpStatus.BAD_REQUEST, ex);
        return new ResponseEntity<>(new Response(ex.getParameterName()+" parameter is missing")
                , errorObject.getStatus());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> wrongMethod(Exception ex){
        ErrorObject errorObject = new ErrorObject(HttpStatus.METHOD_NOT_ALLOWED, ex);
        return makeResponse(errorObject);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleError(Exception ex){
        ErrorObject errorObject = new ErrorObject(HttpStatus.INTERNAL_SERVER_ERROR, ex);
        errorObject.setTrace(null);
        return new ResponseEntity<>(errorObject,  errorObject.getStatus());
    }
}
