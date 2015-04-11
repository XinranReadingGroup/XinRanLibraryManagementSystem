package com.xinran.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

import com.xinran.pojo.Book;

@Repository(value = "bookMapper")
public interface BookMapper {    
    
    @Select("SELECT * FROM book   order by created_At asc LIMIT #{limit} OFFSET #{offset} ")
    public List<Book> findAllWithPagenate(@Param("limit") int limit, @Param("offset") int offset);
    
    
    @Select("SELECT * FROM book WHERE isbn = #{isbn}")
    Book findByISBN(@Param("isbn") String isbn);
    
    @Insert("INSERT INTO book(created_At,updated_At,isbn,title,img_url,author,summary,price,publisher) values(now(),now(),#{isbn},#{title},#{imgURL},#{author},#{summary},#{price},#{publisher})")
    @SelectKey(before = false, keyProperty = "id", resultType = Long.class, statement = { "SELECT LAST_INSERT_ID() AS id" })
    Long add(Book book);
}