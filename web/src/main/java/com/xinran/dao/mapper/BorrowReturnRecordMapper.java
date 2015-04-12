package com.xinran.dao.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.xinran.pojo.BorrowReturnRecord;
import com.xinran.pojo.User;

/**
 * @author 高海军 帝奇 Apr 12, 2015 3:59:56 PM
 */
@Repository(value = "borrowReturnRecordMapper")
public interface BorrowReturnRecordMapper {

    @Insert("INSERT INTO borrow_return_record(created_At,updated_At,borrow_user_id,owner_user_id,on_off_stock_id,book_id,book_type,borrow_date,return_date,borrow_status) "
            + " values(now(),now(),#{borrowUserId},#{ownerUserId},#{onOffStockId},#{bookId},#{bookType},#{borrowDate},#{returnDate},#{borrowStatus})")
    @Options(useGeneratedKeys = true)
    void insert(BorrowReturnRecord record);

    @Select("SELECT * FROM borrow_return_record WHERE id = #{id}")
    BorrowReturnRecord findBorrowReturnRecordById(@Param("id") Long id);
    
    @Update("update  borrow_return_record set   updated_At = #{updatedAt},borrow_user_id =  #{borrowUserId}, owner_user_id = #{ownerUserId},"
            + " on_off_stock_id=#{onOffStockId},book_id=#{bookId}, book_type= #{bookType},borrow_date =#{borrowDate},"
            + " reset_Password_Sent_At =#{resetPasswordSentAt},remember_Created_At=#{rememberCreatedAt}"
            + ", return_date =#{returnDate},borrow_status = #{borrowStatus}  where id = #{id}")
    public int updateUser(User user);


}
