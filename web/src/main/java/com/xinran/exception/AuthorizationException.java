package com.xinran.exception;

/**
 * @author 高海军 帝奇 Jul 6, 2015 9:48:24 PM
 */
public class AuthorizationException extends XinranCheckedException {


    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    public AuthorizationException(int code) {
        super(code);
    }


    public AuthorizationException() {
        super();
    }


    private static final long serialVersionUID = 8524794054727899533L;

}
