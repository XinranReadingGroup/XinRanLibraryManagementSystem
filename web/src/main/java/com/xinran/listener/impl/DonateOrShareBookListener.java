package com.xinran.listener.impl;

import com.xinran.event.impl.BookOnStockEvent;
import org.springframework.stereotype.Service;

import com.xinran.constant.ScoreReason;
import com.xinran.constant.ScoreValueConstant;
import com.xinran.event.Event;
import com.xinran.pojo.OnOffStockRecord;
import com.xinran.pojo.Score;

/**
 * @author 高海军 帝奇 Apr 18, 2015 2:18:43 PM
 */
@Service(value = "donateOrShareBookListener")
// @Configurable
public class DonateOrShareBookListener extends CommonScoreListener {


    @Override
    public void onEvent(Event event) {
        BookOnStockEvent bookEvent = (BookOnStockEvent) event;
        OnOffStockRecord record = bookEvent.getOnOffStockRecord();
        Score score = new Score();
        score.setFactId(record.getId());
        score.setScoreValue(ScoreValueConstant.SHARE_OR_DONATE_SCORE);
        score.setScoreReason(ScoreReason.DONATE_BOOK.getReason());
        Long userId = record.getOwnerUserId();
        score.setUserId(userId);

        super.addScoreAndUpdateUserSumScore(score);

    }



}
