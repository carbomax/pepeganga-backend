package uy.pepeganga.meli.service.exceptions;

public class OrderCreateException extends Exception{

    public OrderCreateException(String message) {
        super(message);
    }

    public OrderCreateException(String message, Throwable cause) {
        super(message, cause);
    }
}
