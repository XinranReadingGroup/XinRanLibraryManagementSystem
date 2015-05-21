package com.xinran.pojo;

import java.util.Date;

import lombok.Data;

import com.xinran.util.DateUtil;

/**
 * @author 高海军 帝奇 Apr 18, 2015 2:25:00 PM
 */
@Data
public class Score {

    public Score() {
        Date now = DateUtil.getCurrentDate();
        this.createdAt = now;
        this.updatedAt = now;
    }

    private Long id;
    private Date createdAt;
    private Date updatedAt;

    private Long    userId;     // 积分拥有者
    private Long    factId;     // OnStockRecordId(捐书id),RorrowBookRecordId(捐书id)等
    private Integer scoreReason; // 见枚举类 ScoreReason
    private Integer scoreValue; // 单词奖励或者惩罚分数,可正可负

}
