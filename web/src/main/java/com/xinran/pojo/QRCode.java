package com.xinran.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xinran.util.DateUtil;
import lombok.Data;

import java.util.Date;

/**
 * Created by 高海军 帝奇 on 2015 Feb 13 07:32.
 */
@Data
public class QRCode {

    public static final int NOT_USERD = 1;

    public static final int USERD = 2;


    public QRCode() {
        Date now = DateUtil.getCurrentDate();
        this.createdAt = now;
        this.updatedAt = now;
    }

    private Long    id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date    createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date    updatedAt;

    private String type;
    private String content;
    private int status;


}
