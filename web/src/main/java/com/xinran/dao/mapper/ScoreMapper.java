package com.xinran.dao.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.xinran.pojo.Score;

public interface ScoreMapper {

    // TODO add index,4个唯一字段

    @Select("SELECT * FROM score WHERE id = #{id}")
    public Score findUserById(@Param("id") Long id);

    @Select("SELECT sum(score_value) FROM score WHERE user_id = #{userId}")
    public Integer queryTotalScoreByUserId(@Param("userId") Long userId);

    @Insert("insert into score (created_At, updated_At,user_Id, fact_Id,score_Reason,score_Value)  values ( now(),now(),#{userId}, #{factId},"
            + " #{scoreReason}, #{scoreValue} )")
    // @Options(useGeneratedKeys = true, keyProperty = "id")
    @Options(useGeneratedKeys = true)
    public void addScore(Score score);
    
    


}
