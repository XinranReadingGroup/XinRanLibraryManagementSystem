package com.xinran.dao.mapper;

import java.awt.Menu;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.xinran.pojo.Book;

@Repository(value = "bookMapper")
public interface BookMapper {    
    
    @Select(value = "${sql}")    
    @Results(value = { @Result(id = true, property = "id", column = "id"),    
            @Result(property = "parentId", column = "c_parent_id"),    
            @Result(property = "url", column = "c_url"),    
            @Result(property = "isShowLeft", column = "c_is_show_left"),    
            @Result(property = "name", column = "c_name") })    
    List<Menu> operateReturnBeans(@Param(value = "sql") String sql);    

    @Select("SELECT * FROM book   order by created_At asc LIMIT #{limit} OFFSET #{offset} ")
    public List<Book> findAllWithPagenate(@Param("limit") int limit, @Param("offset") int offset);
}