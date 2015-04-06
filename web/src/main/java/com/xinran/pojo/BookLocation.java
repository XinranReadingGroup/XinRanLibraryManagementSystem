/*
 * Copyright 1999-2014 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.xinran.pojo;

import lombok.Data;

/**
 * @author 高海军 帝奇 Apr 3, 2015 7:49:38 PM
 */
@Data
public class BookLocation {

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
