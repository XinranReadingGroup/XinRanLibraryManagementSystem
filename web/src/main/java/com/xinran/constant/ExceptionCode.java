package com.xinran.constant;

/**
 * @author 高海军 帝奇 Apr 9, 2015 10:18:41 PM
 */
public enum ExceptionCode {
    // sign up
    IndentifierAlreadyBeenTaken(10000),


    // sign in
    InvalidUserNameOrPassowrd(20000),


    ;

    private int code;

    private ExceptionCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
