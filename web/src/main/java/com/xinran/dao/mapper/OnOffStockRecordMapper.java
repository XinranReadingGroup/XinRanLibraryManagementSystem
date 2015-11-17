package com.xinran.dao.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.xinran.pojo.OnOffStockRecord;
import com.xinran.pojo.Pagination;

@Repository(value = "onOffStockRecordMapper")
public interface OnOffStockRecordMapper {    
    
    @Insert("INSERT INTO on_off_stock_record(created_at,updated_at,book_id,book_type,owner_user_id,owner_phone,location,on_stock_date,off_stock_date,borrow_status) "
            + " values(now(),now(),#{bookId},#{bookType},#{ownerUserId},#{ownerPhone},#{location},#{onStockDate},#{offStockDate},#{borrowStatus})")
    @SelectKey(before = false, keyProperty = "id", resultType = Long.class, statement = { "SELECT LAST_INSERT_ID() AS id" })
    Long add(OnOffStockRecord record);
    
    @Select("SELECT * FROM on_off_stock_record WHERE off_stock_date is null and id = #{id}")
    public OnOffStockRecord findOnOffStockRecordById(@Param("id") Long id);

    @Update("update  on_off_stock_record set   updated_At = #{updatedAt},off_Stock_Date =  #{offStockDate},"
            + "owner_Phone =  #{ownerPhone}, location = #{location},borrow_Status=#{borrowStatus},"
            + " borrow_user_id=#{borrowUserId},borrow_Id=#{borrowId}   where id = #{id}")
    public int updateOnOffStockRecord(OnOffStockRecord record);
    
    // TODO add index
    @Select("SELECT * FROM on_off_stock_record WHERE off_stock_date is null and owner_user_id = #{record.ownerUserId} and book_type = #{record.bookType} order by id desc limit #{page.start},#{page.end}")
    List<OnOffStockRecord> findOnlyOnStockRecordListByCondition(@Param("record") OnOffStockRecord record, @Param("page") Pagination page);
    
 // TODO add index
    @Select("SELECT * FROM on_off_stock_record WHERE off_stock_date is null  order by id desc limit #{page.start},#{page.end}")
    List<OnOffStockRecord> findOnlyOnStockRecordList( @Param("page") Pagination page);

}