
package com.xinran.pojo;

import java.util.Date;

import com.xinran.util.DateUtil;

import lombok.Data;

/**
 * 书籍位置
 * 
 * @author 高海军 帝奇 Apr 3, 2015 7:49:38 PM
 */
@Data
public class BookLocation {

    private Long   id;
    private Date   createdAt;
    private Date   updatedAt;

    public BookLocation() {
        Date now = DateUtil.getCurrentDate();
        this.createdAt = now;
        this.updatedAt = now;
    }

    private Float  lat;
    private Float  lng;

    private String type;     // 小范围, 国内的

    private String province;
    private String city;
    private String country;
    private String detail;
}
