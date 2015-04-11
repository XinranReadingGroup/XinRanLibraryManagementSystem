
package com.xinran.constant;


/**
 * @author 高海军 帝奇 Apr 3, 2015 7:27:02 PM
 */

public enum BookStatus {

    DONATED(0), SHARED(1);

    private Integer status;

    private BookStatus(int value) {
        this.status = Integer.valueOf(value);
    }
    
    public Integer getStatus(){
    	return status;
    }

}
