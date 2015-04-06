
package com.xinran.constant;


/**
 * @author 高海军 帝奇 Apr 3, 2015 7:27:02 PM
 */

public enum BookLocationType {

    little(1), // 滨江地区

    big(0); // 全国范围

    private int type;

    private BookLocationType(int status) {
        this.type = status;
    }

    public int getType() {
        return type;
    }



}
