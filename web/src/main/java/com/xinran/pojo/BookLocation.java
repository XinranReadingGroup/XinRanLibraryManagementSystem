
package com.xinran.pojo;

import java.util.Date;

import lombok.Data;

/**
 * @author 高海军 帝奇 Apr 3, 2015 7:49:38 PM
 */
@Data
public class BookLocation {

    private Long   id;
    private Date   createdAt;
    private Date   updatedAt;

    private Float  lat;
    private Float  lng;

    // `lat` FLOAT( 10, 6 ) NOT NULL ,
    // `lng` FLOAT( 10, 6 ) NOT NULL

    private String type;     // 小范围, 国内的

    private String province;
    private String city;
    private String country;
    private String detail;
}
