package com.xinran.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinran.dao.mapper.ScoreMapper;
import com.xinran.pojo.Score;
import com.xinran.service.ScoreService;

/**
 * @author 高海军 帝奇 Apr 18, 2015 3:47:24 PM
 */
@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private ScoreMapper scoreMapper;

    public Score findScoreById(Long id) {
        return scoreMapper.findUserById(id);
    }

    public Integer queryTotalScoreByUserId(Long userId) {
        return scoreMapper.queryTotalScoreByUserId(userId);
    }

    public void addScore(Score score) {
        scoreMapper.addScore(score);
    }

}
