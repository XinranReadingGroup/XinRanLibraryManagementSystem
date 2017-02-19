package com.xinran.constant;

/**
 * @author 高海军 帝奇 Apr 9, 2015 10:18:41 PM
 */
public enum SystemResultCode {
	
	
	UserNotLoginedInOrAccessTokenInvalid(403,"用户未登录,或者登录Token已经失效"),
	
    // sign up
    IndentifierAlreadyBeenTaken(10000,"该账号(邮箱或者用户名称)已经被注册,请替换成一个新的"),


    // sign in
    InvalidUserNameOrPassowrd(20000,"用户名或者密码不正确"),

    // view user profiler
    CantViewUserProfilerIfItIsNotYours(20001,"只能查看你自己的用户信息"),


    NoPreAssignedQrCodeFound(20100,"不能查到系统中已经分配的二维码"),

    QrcodeAlReadyBeenTaken(20110,"该二维码已与其他书关联,请换一个二维码"),

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

    // 错误的活动状态查询
    WrongActivityQueryStatus(50001,"错误的活动状态查询"),
	
	BAD_ACTIVITY_STATUS(60001,"营销活动的查询条件错误"),




    ;

    private int code;
    
    private String desc;

    public String getDesc() {
		return desc;
	}

	private SystemResultCode(int code,String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

}
