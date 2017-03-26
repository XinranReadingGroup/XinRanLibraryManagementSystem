package com.xinran.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.xinran.pojo.BorrowReturnRecord;
import com.xinran.pojo.Pagination;

/**
 * @author 高海军 帝奇 Apr 12, 2015 3:59:56 PM
 */
@Repository(value = "borrowReturnRecordMapper")
public interface BorrowReturnRecordMapper {

    @Insert("INSERT INTO borrow_return_record(created_at,updated_at,borrow_user_id,owner_user_id,on_off_stock_id,book_id,book_type,borrow_date,return_date,borrow_status) "
            + " values(now(),now(),#{borrowUserId},#{ownerUserId},#{onOffStockId},#{bookId},#{bookType},#{borrowDate},#{returnDate},#{borrowStatus})")
    @Options(useGeneratedKeys = true)
    void insert(BorrowReturnRecord record);

    @Select("SELECT * FROM borrow_return_record WHERE id = #{id}")
    BorrowReturnRecord findById(@Param("id") Long id);
    
    @Update("update  borrow_return_record set   updated_at = now(),return_date = #{returnDate},borrow_status = #{borrowStatus}  where id = #{id}")
    public int updateBorrowReturnRecord(BorrowReturnRecord record);


    @Select("SELECT * FROM borrow_return_record WHERE borrow_user_id = #{borrowUserId} and borrow_status = #{borrowStatus} order by id desc limit #{page.start},#{page.end}")
    List<BorrowReturnRecord> findBorrowedOrReturnedRecordsByBorrowUserId(@Param("borrowUserId") Long borrowUserId,
                                                                         @Param("borrowStatus") Integer borrowStatus,
                                                                         @Param("page") Pagination page);


    @Select("SELECT * FROM borrow_return_record WHERE  on_off_stock_id = #{onOffStockId} order by id desc limit #{page.start},#{page.end}")
    List<BorrowReturnRecord> findAllBorrowedAndReturnedRecordsByOnOffStockId(@Param("onOffStockId") Long onOffStockId,
                                                                             @Param("page") Pagination page);


    @Select("SELECT * FROM borrow_return_record WHERE  on_off_stock_id = #{onOffStockId} and borrow_status = 1 order by id desc limit #{page.start},#{page.end}")
    List<BorrowReturnRecord> findOnlyBorrowingRecordsByOnOffStockId(@Param("onOffStockId") Long onOffStockId,
                                                                    @Param("page") Pagination page);

    @Select("SELECT * FROM borrow_return_record WHERE  on_off_stock_id = #{onOffStockId} and borrow_status = 0 order by id desc limit #{page.start},#{page.end}")
    List<BorrowReturnRecord> findOnlyReturnedRecordsByOnOffStockId(@Param("onOffStockId") Long onOffStockId,
                                                       @Param("page") Pagination page);

}
