package com.xinran.dao.mapper;


import java.util.List;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import com.xinran.pojo.OnOffStockRecord;
import com.xinran.pojo.Pagination;

@Repository(value = "onOffStockRecordMapper")
public interface OnOffStockRecordMapper {    
    
    @Insert("INSERT INTO on_off_stock_record(created_at,updated_at,book_id,book_type,qr_code_id,owner_user_id,owner_phone,location,on_stock_date,off_stock_date,borrow_status) "
            + " values(now(),now(),#{bookId},#{bookType},#{qrCodeId},#{ownerUserId},#{ownerPhone},#{location},#{onStockDate},#{offStockDate},#{borrowStatus})")
//    @SelectKey(before = false, keyProperty = "id", resultType = Long.class, statement = { "SELECT LAST_INSERT_ID() AS id" })
    @Options(useGeneratedKeys = true)
    Long add(OnOffStockRecord record);
    
    @Select("SELECT * FROM on_off_stock_record WHERE off_stock_date is null and id = #{id}")
      OnOffStockRecord findOnOffStockRecordById(@Param("id") Long id);

    @Select("SELECT * FROM on_off_stock_record WHERE  qr_code_id = #{qrCodeId}")
    OnOffStockRecord findOnOffStockRecordByQRCodeId(@Param("qrCodeId") Long qrCodeId);

//    @Select("SELECT * FROM on_off_stock_record WHERE off_stock_date is null and book_id =ANY(#{bookIds}::long[])")
    @Select({"<script>",
        " SELECT * ", 
        "FROM on_off_stock_record ",
        " WHERE off_stock_date is null and book_id  IN ", 
          " <foreach item='item' index='index' collection='bookIds' ",
            " open='(' separator=',' close=')'> ",
            " #{item} ",
          " </foreach> ",
        "</script>"})

//    @SelectProvider(type = OnOffStockRecordMapperProvider.class, method="in")
      List<OnOffStockRecord> findOnOffStockRecordByBookIds(@Param("bookIds") Long[] bookIds);

    @Update("update  on_off_stock_record set   updated_At = #{updatedAt},off_Stock_Date =  #{offStockDate},"
            + "owner_Phone =  #{ownerPhone}, location = #{location},borrow_Status=#{borrowStatus},"
            + " borrow_user_id=#{borrowUserId},borrow_Id=#{borrowId}   where id = #{id}")
      int updateOnOffStockRecord(OnOffStockRecord record);

    //TUNE 针对null做优化。
    @Select("SELECT * FROM on_off_stock_record WHERE off_stock_date is null and owner_user_id = #{record.ownerUserId} and book_type = #{record.bookType} order by id desc limit #{page.start},#{page.end}")
    List<OnOffStockRecord> findOnlyOnStockRecordListByCondition(@Param("record") OnOffStockRecord record, @Param("page") Pagination page);
    
    @Select("SELECT * FROM on_off_stock_record WHERE off_stock_date is null  order by id desc limit #{page.start},#{page.end}")
    List<OnOffStockRecord> findOnlyOnStockRecordList( @Param("page") Pagination page);

}