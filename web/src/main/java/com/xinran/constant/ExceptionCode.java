package com.xinran.constant;

/**
 * @author 高海军 帝奇 Apr 9, 2015 10:18:41 PM
 */
public enum ExceptionCode {
    // sign up
    IndentifierAlreadyBeenTaken(10000),


    // sign in
    InvalidUserNameOrPassowrd(20000),

    // view user profiler
    CantViewUserProfilerIfItIsNotYours(20001),


    // borrow
    InvalidOnOffStockId(30000),

    // 该书已被借走,不能重复再借
    TheBookHasBeenBorrowed(30001),

    // 只允许管理员帮别人捐书、享书
    OnlyAdminHelpToOnStock(31000),

    // 捐书、享书是无法定位到持有者
    NoOwnerWhenOnStock(31001),

    // 下架时该书尚未归还
    ReturnItBeforeOffStock(32000),

    // 没有指定的书用以下架
    NoStockToOff(32001),

    // 只有享书可以下架
    OnlySharedCanOffStock(32002),

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
