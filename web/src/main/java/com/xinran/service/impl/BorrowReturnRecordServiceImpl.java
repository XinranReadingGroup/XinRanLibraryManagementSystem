package com.xinran.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinran.constant.BorrowStatus;
import com.xinran.dao.mapper.BorrowReturnRecordMapper;
import com.xinran.pojo.BorrowReturnRecord;
import com.xinran.pojo.Pagination;
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
        return borrowReturnRecordMapper.findById(id);
    }
    
    public int updateBorrowReturnRecord(BorrowReturnRecord record) {
        return borrowReturnRecordMapper.updateBorrowReturnRecord(record);
    }

    /*
     * (non-Javadoc)
     * @see com.xinran.service.BorrowReturnRecordService#findBorrowedBooks(java.lang.Long, com.xinran.pojo.Pagination)
     */
    @Override
    public List<BorrowReturnRecord> findBorrowedBooksByUserId(Long userId, Pagination page) {
        List<BorrowReturnRecord> borrowReturnRecordList = borrowReturnRecordMapper.findBorrowedOrReturnedRecordsByBorrowUserId(userId,
                                                                                                    BorrowStatus.BORROWED.getStatus(),
                                                                                                    page);
        return borrowReturnRecordList;
    }



    @Override
    public List<BorrowReturnRecord> findReturnedBooksByUserId(Long userId, Pagination page) {
        List<BorrowReturnRecord> borrowReturnRecordList = borrowReturnRecordMapper.findBorrowedOrReturnedRecordsByBorrowUserId(userId,
                                                                                                       BorrowStatus.RETURNED.getStatus(),
                                                                                                       page);
        return borrowReturnRecordList;
    }

    @Override
    public List<BorrowReturnRecord> findAllBorrowedAndReturnedRecordsByOnOffStockId(Long onOffStockId, Pagination page) {
        List<BorrowReturnRecord> borrowReturnRecordList = borrowReturnRecordMapper.findAllBorrowedAndReturnedRecordsByOnOffStockId(onOffStockId,
                page);
        return borrowReturnRecordList;
    }


    @Override
    public List<BorrowReturnRecord> findOnlyBorrowingRecordsByOnOffStockId(Long onOffStockId, Pagination page) {
        List<BorrowReturnRecord> borrowReturnRecordList = borrowReturnRecordMapper.findOnlyBorrowingRecordsByOnOffStockId(onOffStockId,
                                                                                                             page);
        return borrowReturnRecordList;
    }

    @Override
    public List<BorrowReturnRecord> findOnlyReturnedRecordsByOnOffStockId(Long onOffStockId, Pagination page) {
        List<BorrowReturnRecord> borrowReturnRecordList = borrowReturnRecordMapper.findOnlyReturnedRecordsByOnOffStockId(onOffStockId,
                page);
        return borrowReturnRecordList;
    }
}
