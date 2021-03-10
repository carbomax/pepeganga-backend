package uy.com.pepeganga.businessutils.exceptions;

import org.springframework.http.HttpStatus;

public interface HttpStatusPGException {
    HttpStatus getHttpStatus();
}
