package com.xinran.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xinran.util.DateUtil;
import lombok.Data;

/**
 * @author 高海军 帝奇 Jun 1, 2015 8:04:54 PM
 */

@Data
public class Activity {

    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;

    public Activity() {
        Date now = DateUtil.getCurrentDate();
        this.createdAt = now;
        this.updatedAt = now;
    }

    private String title;
    private String memo;

    private Date   startDate;
    private Date   endDate;

    private String type;     // score 积分活动, 返券活动...

    private String action;   // 减积分 sub,add 加积分
    
    private Long   score;    // 分值

}
