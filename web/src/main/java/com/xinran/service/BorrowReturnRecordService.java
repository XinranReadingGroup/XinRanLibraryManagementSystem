package com.xinran.service;

import com.xinran.pojo.BorrowReturnRecord;

/**
 * @author 高海军 帝奇 Apr 12, 2015 3:40:26 PM
 */
public interface BorrowReturnRecordService {

    void insert(BorrowReturnRecord record);

    BorrowReturnRecord findBorrowReturnRecordById(Long id);
    
    int updateBorrowReturnRecord(BorrowReturnRecord record);
}
