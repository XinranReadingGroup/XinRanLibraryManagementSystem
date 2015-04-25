package com.xinran.listener.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinran.constant.ScoreReason;
import com.xinran.constant.ScoreValueConstant;
import com.xinran.event.Event;
import com.xinran.listener.Listener;
import com.xinran.pojo.OnOffStockRecord;
import com.xinran.pojo.Score;
import com.xinran.pojo.User;
import com.xinran.service.ScoreService;
import com.xinran.service.UserService;
import com.xinran.util.ThreadLocalUtil;

/**
 * @author 高海军 帝奇 Apr 18, 2015 2:18:43 PM
 */
@Service(value = "donateOrShareBookAddScoreListerner")
// @Configurable
public class DonateOrShareBookAddScoreListerner implements Listener {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private UserService  userService;

    @Override
    public void onEvent(Event event) {
        // BookOnStockEvent bookEvent = (BookOnStockEvent) event;
        OnOffStockRecord record = (OnOffStockRecord) ThreadLocalUtil.get();
        Score score = new Score();
        score.setFactId(record.getId());
        score.setScoreValue(ScoreValueConstant.DONATE_SCORE);
        score.setScoreReason(ScoreReason.DONATE_BOOK.getReason());
        Long userId = record.getOwnerUserId();
        score.setUserId(userId);

        addScoreAndUpdateUserSumScore(score, userId);

    }

    private void addScoreAndUpdateUserSumScore(Score score, Long borrowUserId) {
        scoreService.addScore(score);

        Integer sumScore = scoreService.queryTotalScoreByUserId(borrowUserId);
        User user = userService.findUserByUserId(borrowUserId);
        user.setScore(sumScore);
        userService.updateUser(user);
    }

}
