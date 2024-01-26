package com.moviesreview.api.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@Log4j2
@ControllerAdvice
public class GlobalExceptionsHandler {

    @ExceptionHandler(MovieNotFoundEx.class)
    public ResponseEntity<ErrorObject> handleMovieNotFoundException(MovieNotFoundEx ex, WebRequest request){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        log.error("Movie not found, ex:{}, request:{}",ex.getMessage(),request);


        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReviewNotFoundEx.class)
    public ResponseEntity<ErrorObject> handleReviewNotFoundEx(ReviewNotFoundEx ex, WebRequest request){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        log.error("Review not found, ex:{}, request:{}",ex.getMessage(),request);


        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NOT_FOUND);
    }

}
