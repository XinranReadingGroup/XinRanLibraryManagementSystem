package com.xinran.constant;

/**
 * @author 高海军 帝奇 Apr 18, 2015 2:43:17 PM
 */
public enum ScoreReason {
    DONATE_BOOK(0), BORROW_BOOK(1), RETURN_BOOK(2),

    ;

    private Integer reason;

    private ScoreReason(Integer value) {
        this.setReason(value);
    }

    /**
     * @return the reason
     */
    public Integer getReason() {
        return reason;
    }

    /**
     * @param reason the reason to set
     */
    public void setReason(Integer reason) {
        this.reason = reason;
    }
    
}
