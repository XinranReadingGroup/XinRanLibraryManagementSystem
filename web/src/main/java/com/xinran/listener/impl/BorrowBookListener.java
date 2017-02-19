package com.xinran.listener.impl;

import com.xinran.constant.ScoreReason;
import com.xinran.constant.ScoreValueConstant;
import com.xinran.event.Event;
import com.xinran.event.impl.BookOnStockEvent;
import com.xinran.event.impl.BorrowBookEvent;
import com.xinran.listener.Listener;
import com.xinran.pojo.BorrowReturnRecord;
import com.xinran.pojo.OnOffStockRecord;
import com.xinran.pojo.Score;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

/**
 * @author 高海军 帝奇 Apr 18, 2015 2:18:43 PM
 */
//@Configurable(preConstruction = true)
@Service(value = "borrowBookListener")
public class BorrowBookListener extends CommonScoreListener {


    @Override
    public void onEvent(Event event) {
        BorrowBookEvent bookEvent = (BorrowBookEvent) event;
        BorrowReturnRecord record = bookEvent.getBorrowReturnRecord();
        Score score = new Score();
        score.setFactId(record.getId());
        score.setScoreValue(ScoreValueConstant.BORROW_SCORE);
        score.setScoreReason(ScoreReason.BORROW_BOOK.getReason());
        Long userId = record.getOwnerUserId();
        score.setUserId(userId);

        super.addScoreAndUpdateUserSumScore(score);
    }

}
