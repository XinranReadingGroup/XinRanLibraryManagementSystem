package com.xinran.exception;

import com.xinran.constant.SystemResultCode;

/**
 * @author 高海军 帝奇 Apr 9, 2015 10:09:07 PM
 */
public class XinranCheckedException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -5943421879074551385L;

    private int               code;

    public XinranCheckedException(int code,String message) {
        super(message);
        this.code = code;
    }
    
    public XinranCheckedException(SystemResultCode code) {
       this(code.getCode(),code.getDesc());
    }


    /**
     * 
     */
    public XinranCheckedException() {
    }

    public int getCode() {
        return code;
    }

}
