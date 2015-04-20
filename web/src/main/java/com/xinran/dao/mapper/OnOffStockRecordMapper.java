package com.xinran.dao.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.xinran.pojo.OnOffStockRecord;

@Repository(value = "onOffStockRecordMapper")
public interface OnOffStockRecordMapper {    
    
    @Insert("INSERT INTO on_off_stock_record(created_At,updated_At,book_Id,book_Type,owner_User_Id,owner_Phone,location,on_Stock_Date,off_Stock_Date,borrow_Status) "
            + " values(now(),now(),#{bookId},#{bookType},#{ownerUserId},#{ownerPhone},#{location},#{onStockDate},#{offStockDate},#{borrowStatus})")
    @SelectKey(before = false, keyProperty = "id", resultType = Long.class, statement = { "SELECT LAST_INSERT_ID() AS id" })
    Long add(OnOffStockRecord record);
    
    @Select("SELECT * FROM on_off_stock_record WHERE id = #{id}")
    public OnOffStockRecord findOnOffStockRecordById(@Param("id") Long id);

    @Update("update  on_off_stock_record set   updated_At = #{updatedAt},off_Stock_Date =  #{offStockDate},"
            + "owner_Phone =  #{ownerPhone}, location = #{location},borrow_Status=#{borrowStatus},"
            + " borrowUserId=#{borrowUserId},borrowId=#{borrowId}   where id = #{id}")
    public int updateOnOffStockRecord(OnOffStockRecord record);


}