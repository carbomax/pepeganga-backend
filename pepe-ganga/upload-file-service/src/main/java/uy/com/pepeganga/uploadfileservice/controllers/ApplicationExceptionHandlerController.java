package uy.com.pepeganga.uploadfileservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uy.com.pepeganga.business.common.exceptions.PGException;
import uy.com.pepeganga.uploadfileservice.exceptions.AwsBucketException;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ApplicationExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({PGException.class, AwsBucketException.class})
    protected ResponseEntity<PGException> bucketException(PGException exception) {
        if(exception instanceof AwsBucketException){
            AwsBucketException bucketException = (AwsBucketException) exception;
            return new ResponseEntity<>(PGException.builder(bucketException), bucketException.getHttpStatus());
        } else return new ResponseEntity<>(new PGException(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<PGException> exception(Exception exception) {
        return new ResponseEntity<>(new PGException(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<PGException> runtimeException(Exception exception) {
        return new ResponseEntity<>(new PGException(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<PGException> handleConstraint(ConstraintViolationException exception) {
        return new ResponseEntity<>(new PGException(exception.getMessage(), HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }


}
