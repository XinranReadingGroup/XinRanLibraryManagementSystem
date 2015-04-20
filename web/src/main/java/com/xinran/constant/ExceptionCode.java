package com.xinran.constant;

/**
 * @author 高海军 帝奇 Apr 9, 2015 10:18:41 PM
 */
public enum ExceptionCode {
    // sign up
    IndentifierAlreadyBeenTaken(10000),


    // sign in
    InvalidUserNameOrPassowrd(20000),


    // borrow
    InvalidOnOffStockId(30000),

    // 该书已被借走,不能重复再借
    TheBookHasBeenBorrowed(30001),

    // return 还书时,该书应该处于已被借阅状态
    TheBookShouldBeInBorrowedStatus(40000),

    // return 还书时,只能还自己借的书
    TheBookYouReturnedShouldBeBorrowedByYou(40001),


    ;

    private int code;

    private ExceptionCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
