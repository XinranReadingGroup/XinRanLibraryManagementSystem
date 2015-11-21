package com.xinran.exception;

import com.xinran.constant.SystemResultCode;

/**
 * @author 高海军 帝奇 Apr 8, 2015 10:03:06 PM
 */
public class BorrowOrReturnValidationException extends XinranCheckedException {

    /**
     * @param code
     */
    public BorrowOrReturnValidationException(int code,String message) {
    	super(code, message);
    }
    
    public BorrowOrReturnValidationException(SystemResultCode code) {
    	super(code);
    }
    
    /**
     * 
     */
    private static final long serialVersionUID = -4628485572389136720L;

}
