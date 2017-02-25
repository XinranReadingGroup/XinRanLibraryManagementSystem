package com.xinran.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinran.constant.BookType;
import com.xinran.constant.BorrowStatus;
import com.xinran.constant.SystemResultCode;
import com.xinran.dao.mapper.OnOffStockRecordMapper;
import com.xinran.dao.mapper.UserMapper;
import com.xinran.exception.StockException;
import com.xinran.pojo.OnOffStockRecord;
import com.xinran.pojo.Pagination;
import com.xinran.pojo.User;
import com.xinran.service.OnOffStockRecordService;
import com.xinran.service.UserService;

/**
 * @author 高海军 帝奇 Apr 12, 2015 3:40:57 PM
 */
@Service
public class OnOffStockRecordServiceImpl implements OnOffStockRecordService {

    @Autowired
    private OnOffStockRecordMapper onOffStockRecordMapper;
    @Autowired
    private UserService userService;

    @Override
    public OnOffStockRecord onStock(OnOffStockRecord record) throws StockException {
       onOffStockRecordMapper.add(record);
        return record;
    }

    @Override
    public void offStock(OnOffStockRecord record) throws StockException {
        OnOffStockRecord exist = onOffStockRecordMapper.findOnOffStockRecordById(record.getId());
        // 如果未存在或者已下架
        if (exist == null) {
            throw new StockException(SystemResultCode.NoStockToOff );
        }
        // 如果处于借出状态
        if (exist.getBorrowStatus() != null && exist.getBorrowStatus() == BorrowStatus.BORROWED.getStatus()) {
            throw new StockException(SystemResultCode.ReturnItBeforeOffStock );
        }
        // 如果不是享书则无法下架
        if (exist.getBookType() != null && exist.getBookType() != BookType.SHARED.getType()) {
            throw new StockException(SystemResultCode.OnlySharedCanOffStock );
        }
        exist.setOffStockDate(new Date());
        onOffStockRecordMapper.updateOnOffStockRecord(exist);
    }

    @Override
    public OnOffStockRecord findOnOffStockRecordById(Long id) {
        OnOffStockRecord record = onOffStockRecordMapper.findOnOffStockRecordById(id);
        return record;
    }

    @Override
    public OnOffStockRecord findOnOffStockRecordByQRCodeId( Long qrCodeId){
        OnOffStockRecord record = onOffStockRecordMapper.findOnOffStockRecordByQRCodeId(qrCodeId);
        return record;
    }

    @Override
    public List<OnOffStockRecord> findOnOffStockRecordByBookIds(Long... bookIds) {
        return onOffStockRecordMapper.findOnOffStockRecordByBookIds(bookIds);
    }

    /*
     * (non-Javadoc)
     * @see com.xinran.service.OnOffStockRecordService#updateOnOffStockRecord(com.xinran.pojo.OnOffStockRecord)
     */
    @Override
    public int updateOnOffStockRecord(OnOffStockRecord record) {
        return onOffStockRecordMapper.updateOnOffStockRecord(record);
    }

    @Override
    public List<OnOffStockRecord> findShared(Long userId, Pagination page) {
        if (userId == null) {
            throw new IllegalArgumentException();
        }
        if (page == null) {
            page = new Pagination();
        }
        OnOffStockRecord record = new OnOffStockRecord();
        record.setOwnerUserId(userId);
        record.setBookType(BookType.SHARED.getType());
        return onOffStockRecordMapper.findOnlyOnStockRecordListByCondition(record, page);
    }

    @Override
    public List<OnOffStockRecord> findDonated(Long userId, Pagination page) {
        if (userId == null) {
            throw new IllegalArgumentException();
        }
        if (page == null) {
            page = new Pagination();
        }
        OnOffStockRecord record = new OnOffStockRecord();
        record.setOwnerUserId(userId);
        record.setBookType(BookType.DONATED.getType());
        return onOffStockRecordMapper.findOnlyOnStockRecordListByCondition(record, page);
    }
    
    @Override
    public List<OnOffStockRecord> findOnlyOnStockRecordList( Pagination page) {
        if (page == null) {
            page = new Pagination();
        }
        return onOffStockRecordMapper.findOnlyOnStockRecordList( page);
    }
    
}
