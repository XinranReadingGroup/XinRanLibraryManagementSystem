package com.xinran.exception;

import com.xinran.constant.ExceptionCode;

/**
 * @author 高海军 帝奇 Apr 8, 2015 10:03:06 PM
 */
public class UserException extends XinranCheckedException {

    /**
     * @param code
     */
    public UserException(int code,String message) {
    	super(code, message);
    }
    
    public UserException(ExceptionCode code) {
    	super(code);
    }
    

    /**
     * 
     */
    private static final long serialVersionUID = -4628485572389136720L;

}
