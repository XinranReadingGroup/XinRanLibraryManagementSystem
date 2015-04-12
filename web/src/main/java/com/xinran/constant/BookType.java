
package com.xinran.constant;


/**
 * @author 高海军 帝奇 Apr 3, 2015 7:27:02 PM
 */

public enum BookType {

    DONATED(0), SHARED(1);

    private Integer type;

    private BookType(int value) {
        this.type = Integer.valueOf(value);
    }

    public Integer getType() {
        return type;
    }
    


}
