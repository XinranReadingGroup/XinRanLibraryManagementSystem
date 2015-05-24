package com.xinran.dao.mapper;

import com.xinran.pojo.Book;
import com.xinran.pojo.BookLocation;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Insert("INSERT INTO book_location(created_at,updated_at,province,city,county,detail,lat,lng,type) VALUES(now(),now(),#{province},#{city},#{county},#{detail},#{lat},#{lng},#{type})")
    @SelectKey(before = false, keyProperty = "id", resultType = Long.class, statement = { "SELECT LAST_INSERT_ID() AS id" })
    Long add(BookLocation location);


}
