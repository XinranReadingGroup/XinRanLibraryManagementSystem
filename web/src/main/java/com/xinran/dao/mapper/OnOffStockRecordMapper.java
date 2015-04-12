package com.xinran.dao.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

import com.xinran.pojo.OnOffStockRecord;

@Repository(value = "onOffStockRecordMapper")
public interface OnOffStockRecordMapper {    
    
    @Insert("INSERT INTO on_off_stock_record(created_At,updated_At,book_Id,book_Type,owner_User_Id,owner_Phone,location,on_Stock_Date,off_Stock_Date,borrow_Status) "
            + " values(now(),now(),#{bookId},#{bookType},#{ownerUserId},#{ownerPhone},#{location},#{onStockDate},#{offStockDate},#{borrowStatus})")
    @SelectKey(before = false, keyProperty = "id", resultType = Long.class, statement = { "SELECT LAST_INSERT_ID() AS id" })
    Long add(OnOffStockRecord record);
    
}