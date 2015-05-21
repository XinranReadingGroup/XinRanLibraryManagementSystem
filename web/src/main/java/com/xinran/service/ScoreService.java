package com.xinran.service;

import com.xinran.pojo.Score;

/**
 * @author 高海军 帝奇 Apr 18, 2015 3:47:24 PM
 */
public interface ScoreService {

    Score findScoreById(Long id);

    Integer queryTotalScoreByUserId(Long userId);

    void addScore(Score score);

}
