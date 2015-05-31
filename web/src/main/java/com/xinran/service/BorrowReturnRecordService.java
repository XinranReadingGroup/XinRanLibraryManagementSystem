package com.xinran.service;

import java.util.List;

import com.xinran.pojo.BorrowReturnRecord;
import com.xinran.pojo.Pagination;

/**
 * @author 高海军 帝奇 Apr 12, 2015 3:40:26 PM
 */
public interface BorrowReturnRecordService {

    void insert(BorrowReturnRecord record);

    BorrowReturnRecord findBorrowReturnRecordById(Long id);
    
    int updateBorrowReturnRecord(BorrowReturnRecord record);

    List<BorrowReturnRecord> findBorrowedBooks(Long userId, Pagination page);

    List<BorrowReturnRecord> findReturnedBooks(Long userId, Pagination page);

    List<BorrowReturnRecord> findHistroicBorrowedBooks(Long onOffStockId, Pagination page);

}
