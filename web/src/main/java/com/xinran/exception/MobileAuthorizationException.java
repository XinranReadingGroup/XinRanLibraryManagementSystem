package com.xinran.exception;

/**
 * @author 高海军 帝奇 Jul 6, 2015 9:48:24 PM
 */
public class MobileAuthorizationException extends XinranCheckedException {


    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    public MobileAuthorizationException(int code,String message) {
    	super(code, message);
    }


    public MobileAuthorizationException() {
        super();
    }


    private static final long serialVersionUID = 8524794054727899533L;

}
