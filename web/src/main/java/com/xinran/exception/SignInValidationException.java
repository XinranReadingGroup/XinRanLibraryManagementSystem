package com.xinran.exception;

/**
 * @author 高海军 帝奇 Apr 8, 2015 10:03:06 PM
 */
public class SignInValidationException extends XinranCheckedException {

    /**
     * @param code
     */
    public SignInValidationException(int code) {
        super(code);
    }

    /**
     * 
     */
    private static final long serialVersionUID = -4628485572389136720L;

}
