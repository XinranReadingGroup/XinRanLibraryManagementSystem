package com.xinran.exception;

/**
 * @author 高海军 帝奇 Apr 8, 2015 10:03:06 PM
 */
public class StockException extends XinranCheckedException {

    /**
     * @param code
     */
    public StockException(int code) {
        super(code);
    }

    /**
     *
     */
    private static final long serialVersionUID = -1;
}
