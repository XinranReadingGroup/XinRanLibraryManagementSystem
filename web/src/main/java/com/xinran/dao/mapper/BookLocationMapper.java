package com.xinran.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import com.xinran.pojo.BookLocation;

/**
 * Created by zhuangyao.zy on 2015/5/24.
 */
@Repository(value = "bookLocationMapper")
public interface BookLocationMapper {


    @SelectProvider(type = BookLocationMapperProvider.class, method="genQuerySql")
    List<BookLocation> query(@Param("province") String province,@Param("city") String city,@Param("county") String county);

    @Select("SELECT * FROM book_location WHERE id = #{id}")
    BookLocation findBookLocationById(@Param("id") Long id);

    @Insert("INSERT INTO book_location(created_at,updated_at,province,city,county,detail,lat,lng,type) VALUES(now(),now(),#{province},#{city},#{county},#{detail},#{lat},#{lng},#{type})")
    @Options(useGeneratedKeys = true)
    void add(BookLocation location);


}
