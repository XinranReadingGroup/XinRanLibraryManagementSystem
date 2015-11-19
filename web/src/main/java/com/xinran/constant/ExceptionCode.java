package com.xinran.constant;

/**
 * @author 高海军 帝奇 Apr 9, 2015 10:18:41 PM
 */
public enum ExceptionCode {
	
	
    // sign up
    IndentifierAlreadyBeenTaken(10000,"该账号已经被注册,请替换成一个新的账号"),


    // sign in
    InvalidUserNameOrPassowrd(20000,"用户名或者密码不正确"),

    // view user profiler
    CantViewUserProfilerIfItIsNotYours(20001,"只能查看你自己的用户信息"),


    // borrow
    InvalidOnOffStockId(30000,"该书籍不存在"),

    // 该书已被借走,不能重复再借
    TheBookHasBeenBorrowed(30001,"该书已被借走,不能重复再借"),

    // 只允许管理员帮别人捐书、享书
    OnlyAdminHelpToOnStock(31000,"只允许管理员帮别人捐书、享书"),

    // 捐书、享书是无法定位到持有者
    NoOwnerWhenOnStock(31001,"捐书、享书是无法定位到持有者"),

    // 下架时该书尚未归还
    ReturnItBeforeOffStock(32000,"下架时该书尚未归还"),

    // 没有指定的书用来下架
    NoStockToOff(32001,"没有指定的书用来下架"),

    // 只有享书可以下架
    OnlySharedCanOffStock(32002,"只有享书可以下架"),

    // return 还书时,该书应该处于已被借阅状态
    TheBookShouldBeInBorrowedStatus(40000,"还书时,该书应该处于已被借阅状态"),

    // return 还书时,只能还自己借的书
    TheBookYouReturnedShouldBeBorrowedByYou(40001,"还书时,只能还自己借的书"),


    ;

    private int code;
    
    private String desc;

    public String getDesc() {
		return desc;
	}

	private ExceptionCode(int code,String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

}
