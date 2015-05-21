package com.xinran.exception;

/**
 * @author 高海军 帝奇 Apr 8, 2015 10:03:06 PM
 */
public class UserException extends XinranCheckedException {

    /**
     * @param code
     */
    public UserException(int code) {
        super(code);
    }

    /**
     * 
     */
    private static final long serialVersionUID = -4628485572389136720L;

}
