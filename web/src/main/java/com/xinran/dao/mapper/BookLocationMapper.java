package com.xinran.dao.mapper;

import com.xinran.pojo.BookLocation;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhuangyao.zy on 2015/5/24.
 */
@Repository(value = "bookLocationMapper")
public interface BookLocationMapper {


    @SelectProvider(type = BookLocationMapperProvider.class, method="genQuerySql")
    List<BookLocation> query(@Param("province") String province,@Param("city") String city,@Param("county") String county);

    @Select("select * from book_location group by province")
    List<BookLocation> queryProvince();

    @Select("select * from book_location where province=#{province}")
    List<BookLocation> queryCities(@Param("province") String province);

    @Select("select * from book_location where province=#{province} and city=#{city}")
    List<BookLocation> queryCounties(@Param("province") String province,@Param("city") String city);

    @Select("SELECT * FROM book_location WHERE id = #{id}")
    BookLocation findBookLocationById(@Param("id") Long id);

    @Insert("INSERT INTO book_location(created_at,updated_at,user_id,province,city,county,detail,lat,lng,type) VALUES(now(),now(),#{userId},#{province},#{city},#{county},#{detail},#{lat},#{lng},#{type})")
    @Options(useGeneratedKeys = true)
    void add(BookLocation location);

    @Update("update  book_location set   updated_at = now(),user_id = #{userId},province = #{province},city=#{city},county=#{county},detail=#{detail},lat=#{lat},lng=#{lng},type=#{type} where id = #{id}")
    int update(BookLocation location);

    @Delete("delete from book_location where id= #{id}")
    int delete(@Param("id") Long id);


}
