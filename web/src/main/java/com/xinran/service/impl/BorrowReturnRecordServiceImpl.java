package com.xinran.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinran.dao.mapper.BorrowReturnRecordMapper;
import com.xinran.pojo.OnOffStockRecord;
import com.xinran.service.BorrowReturnRecordService;

/**
 * @author 高海军 帝奇 Apr 12, 2015 3:40:57 PM
 */
@Service
public class BorrowReturnRecordServiceImpl implements BorrowReturnRecordService {

    @Autowired
    private BorrowReturnRecordMapper borrowReturnRecordMapper;

    public void borrowBook(OnOffStockRecord record) {

    }
    
    public void returnBook(Long id) {

    }
}
