package uy.pepeganga.meli.service.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uy.com.pepeganga.business.common.exceptions.PGException;
import uy.pepeganga.meli.service.exceptions.MeliAccountNotFoundException;


@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(MeliAccountNotFoundException.class)
    public ResponseEntity<PGException> handlerBusinessException(MeliAccountNotFoundException e){
        return new ResponseEntity<>(ApplicationExceptionHandler.pgExceptionBuilder(e), e.getHttpStatus());
    }

    public static PGException pgExceptionBuilder(PGException exception){
        if(exception instanceof MeliAccountNotFoundException){
            MeliAccountNotFoundException e = (MeliAccountNotFoundException) exception;
            return new PGException(e.getMessage(), e.getError(), e.getStatus(), e.getCauses());
        } else return exception;
    }

}
