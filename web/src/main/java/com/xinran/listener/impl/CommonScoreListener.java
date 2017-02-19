package com.xinran.listener.impl;

import com.xinran.listener.Listener;
import com.xinran.pojo.Score;
import com.xinran.pojo.User;
import com.xinran.service.ScoreService;
import com.xinran.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 高海军 帝奇 74394 on 2017 February  11:18.
 */
@Service
public abstract class CommonScoreListener implements Listener {

    @Autowired
    protected ScoreService scoreService;

    @Autowired
    protected UserService userService;

    protected void addScoreAndUpdateUserSumScore(Score score) {
        scoreService.addScore(score);

        Integer sumScore = scoreService.queryTotalScoreByUserId(score.getUserId());
        User user = userService.findUserByUserId(score.getUserId());
        user.setScore(sumScore);
        userService.updateUser(user);
    }

}
