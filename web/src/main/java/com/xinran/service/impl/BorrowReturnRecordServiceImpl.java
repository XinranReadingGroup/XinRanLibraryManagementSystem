package com.xinran.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinran.dao.mapper.BorrowReturnRecordMapper;
import com.xinran.pojo.BorrowReturnRecord;
import com.xinran.service.BorrowReturnRecordService;

/**
 * @author 高海军 帝奇 Apr 12, 2015 3:40:57 PM
 */
@Service
public class BorrowReturnRecordServiceImpl implements BorrowReturnRecordService {

    @Autowired
    private BorrowReturnRecordMapper borrowReturnRecordMapper;

    public void insert(BorrowReturnRecord record) {
        borrowReturnRecordMapper.insert(record);
    }

    public BorrowReturnRecord findBorrowReturnRecordById(Long id) {
        return borrowReturnRecordMapper.findBorrowReturnRecordById(id);
    }
    
    public int updateBorrowReturnRecord(BorrowReturnRecord record) {
        return borrowReturnRecordMapper.updateBorrowReturnRecord(record);
    }
}
