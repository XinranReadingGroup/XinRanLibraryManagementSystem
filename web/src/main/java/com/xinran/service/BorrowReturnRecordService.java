package com.xinran.service;

import java.util.List;

import com.xinran.constant.BorrowStatus;
import com.xinran.pojo.BorrowReturnRecord;
import com.xinran.pojo.Pagination;

/**
 * @author 高海军 帝奇 Apr 12, 2015 3:40:26 PM
 */
public interface BorrowReturnRecordService {

    void insert(BorrowReturnRecord record);

    BorrowReturnRecord findBorrowReturnRecordById(Long id);
    
    int updateBorrowReturnRecord(BorrowReturnRecord record);

    List<BorrowReturnRecord> findBorrowedBooksByUserId(Long userId, Pagination page);

    List<BorrowReturnRecord> findReturnedBooksByUserId(Long userId, Pagination page);



    List<BorrowReturnRecord> findAllBorrowedAndReturnedRecordsByOnOffStockId(Long onOffStockId, Pagination page);

    List<BorrowReturnRecord> findOnlyBorrowingRecordsByOnOffStockId(Long onOffStockId, Pagination page);

    List<BorrowReturnRecord> findOnlyReturnedRecordsByOnOffStockId(Long onOffStockId, Pagination page);

}
