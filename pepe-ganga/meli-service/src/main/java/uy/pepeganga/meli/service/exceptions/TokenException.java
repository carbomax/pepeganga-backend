package uy.pepeganga.meli.service.exceptions;

public class TokenException extends Exception {

    private int code = 0;


    public TokenException() {
        super();
    }

    public TokenException(int code, String message) {
        super(message);
        this.code = code;
    }

    public TokenException(String message) {
        super(message);
    }

    public TokenException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public TokenException(String message, Throwable cause) {
        super(message, cause);
    }

    protected TokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getCode() {
        return code;
    }

}
