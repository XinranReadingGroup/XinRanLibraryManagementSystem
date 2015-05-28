package com.xinran.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import com.xinran.pojo.BookLocation;
import com.xinran.pojo.BorrowReturnRecord;

/**
 * Created by zhuangyao.zy on 2015/5/24.
 */
@Repository(value = "bookLocationMapper")
public interface BookLocationMapper {

    @Results(value={
            @Result(column="id", property="id", id = true),
            @Result(column="created_at", property="createdAt"),
            @Result(column="updated_at", property="createdAt"),
            @Result(column="province", property="province"),
            @Result(column="city", property="city"),
            @Result(column="county", property="county"),
            @Result(column="detail", property="detail"),
            @Result(column="lat", property="lat"),
            @Result(column="lng", property="lng"),
            @Result(column="type", property="type")
    })
    @SelectProvider(type = BookLocationMapperProvider.class, method="genQuerySql")
    List<BookLocation> query(@Param("province") String province,@Param("city") String city,@Param("county") String county);

    @Select("SELECT * FROM book_location WHERE id = #{id}")
    BookLocation findBookLocationById(@Param("id") Long id);

    @Insert("INSERT INTO book_location(created_at,updated_at,province,city,county,detail,lat,lng,type) VALUES(now(),now(),#{province},#{city},#{county},#{detail},#{lat},#{lng},#{type})")
    @SelectKey(before = false, keyProperty = "id", resultType = Long.class, statement = { "SELECT LAST_INSERT_ID() AS id" })
    Long add(BookLocation location);


}
