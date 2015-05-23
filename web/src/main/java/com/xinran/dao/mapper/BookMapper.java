package com.xinran.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

import com.xinran.pojo.Book;

@Repository(value = "bookMapper")
public interface BookMapper {    
    
    @Select("SELECT * FROM book   order by created_at asc LIMIT #{limit} OFFSET #{offset} ")
    public List<Book> findAllWithPagenate(@Param("limit") int limit, @Param("offset") int offset);
    
    
    @Results(value={
    		@Result(column="id", property="id", id = true),
    		@Result(column="created_at", property="createdAt"),
    		@Result(column="updated_at", property="createdAt"),
    		@Result(column="isbn", property="isbn"),
    		@Result(column="title", property="title"),
    		@Result(column="img_url", property="imgURL"),
    		@Result(column="author", property="author"),
    		@Result(column="summary", property="summary"),
    		@Result(column="price", property="price"),
    		@Result(column="publisher", property="publisher")
    })
    @Select("SELECT * FROM book WHERE isbn = #{isbn}")
    Book findByISBN(@Param("isbn") String isbn);
    
    @Insert("INSERT INTO book(created_at,updated_at,isbn,title,img_url,author,summary,price,publisher) values(now(),now(),#{isbn},#{title},#{imgURL},#{author},#{summary},#{price},#{publisher})")
    @SelectKey(before = false, keyProperty = "id", resultType = Long.class, statement = { "SELECT LAST_INSERT_ID() AS id" })
    Long add(Book book);

    
    @Results(value={
    		@Result(column="id", property="id", id = true),
    		@Result(column="created_at", property="createdAt"),
    		@Result(column="updated_at", property="createdAt"),
    		@Result(column="isbn", property="isbn"),
    		@Result(column="title", property="title"),
    		@Result(column="img_url", property="imgURL"),
    		@Result(column="author", property="author"),
    		@Result(column="summary", property="summary"),
    		@Result(column="price", property="price"),
    		@Result(column="publisher", property="publisher")
    })
    @Select("SELECT * FROM book WHERE id = #{id}")
	Book findById(@Param("id") Long id);


    @Results(value={
    		@Result(column="id", property="id", id = true),
    		@Result(column="created_at", property="createdAt"),
    		@Result(column="updated_at", property="createdAt"),
    		@Result(column="isbn", property="isbn"),
    		@Result(column="title", property="title"),
    		@Result(column="img_url", property="imgURL"),
    		@Result(column="author", property="author"),
    		@Result(column="summary", property="summary"),
    		@Result(column="price", property="price"),
    		@Result(column="publisher", property="publisher")
    })
    @Select("SELECT * FROM book WHERE title like '%#{keyword}%'")
	List<Book> queryByTitle(String keyword);
}