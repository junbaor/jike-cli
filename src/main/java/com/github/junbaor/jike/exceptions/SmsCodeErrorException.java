package com.github.junbaor.jike.exceptions;

public class SmsCodeErrorException extends Exception {

    public SmsCodeErrorException() {
        super();
    }

    public SmsCodeErrorException(String message) {
        super(message);
    }

    public SmsCodeErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public SmsCodeErrorException(Throwable cause) {
        super(cause);
    }

}
