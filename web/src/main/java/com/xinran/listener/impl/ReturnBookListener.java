package com.xinran.listener.impl;

import com.xinran.constant.ScoreReason;
import com.xinran.constant.ScoreValueConstant;
import com.xinran.event.impl.BorrowBookEvent;
import com.xinran.event.impl.ReturnBookEvent;
import com.xinran.pojo.BorrowReturnRecord;
import com.xinran.pojo.Score;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.xinran.event.Event;
import com.xinran.listener.Listener;

/**
 * @author 高海军 帝奇 Apr 18, 2015 2:18:43 PM
 */
//@Configurable(preConstruction = true)
@Service(value = "returnBookListener")
public class ReturnBookListener extends CommonScoreListener {


    @Override
    public void onEvent(Event event) {
        ReturnBookEvent bookEvent = (ReturnBookEvent) event;
        BorrowReturnRecord record = bookEvent.getBorrowReturnRecord();
        Score score = new Score();
        score.setFactId(record.getId());
        score.setScoreValue(ScoreValueConstant.RETURN_SCORE);
        score.setScoreReason(ScoreReason.RETURN_BOOK.getReason());
        Long userId = record.getBorrowUserId();
        score.setUserId(userId);

        super.addScoreAndUpdateUserSumScore(score);
    }

}
