package com.blueyonder.shopdataservice.Exceptions;

public class TooManyCharactersException extends Exception{

    public TooManyCharactersException() {
    }

    public TooManyCharactersException(String message) {
        super(message);
    }

    public TooManyCharactersException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooManyCharactersException(Throwable cause) {
        super(cause);
    }

    public TooManyCharactersException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
