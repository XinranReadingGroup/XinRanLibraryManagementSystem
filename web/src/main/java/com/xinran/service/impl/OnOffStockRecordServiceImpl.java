package com.xinran.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinran.constant.BookType;
import com.xinran.constant.BorrowStatus;
import com.xinran.constant.ExceptionCode;
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
    @Autowired
    private UserMapper userMapper;

    /*
     * TODO submitter 必须存在
     */
    @Override
    public OnOffStockRecord onStock(OnOffStockRecord record) throws StockException {
        User submitter = userMapper.findUserById(record.getOwnerUserId());
        User mobileUser = userMapper.findUserByMobile(record.getOwnerPhone());
        User owner = null;
        // 提交者按目前的逻辑应该是当前登录用户
        // 全部不存在，以手机号创建用户————目前不存在这种情况
        if (submitter == null && mobileUser == null) {
            owner = userService.registerUserByMobile(record.getOwnerPhone());
        }
        // 如果全部存在
        else if (submitter != null && mobileUser != null) {
            // 是同一个用户，则随便取一个
            if (submitter.getId() == mobileUser.getId()) {
                owner = mobileUser;
            }
            // 如果提交者不为管理员，则不能帮别人进行捐、享书
            else if(!userService.isAdmin(submitter)){
                throw new StockException(ExceptionCode.OnlyAdminHelpToOnStock );
            }
        }
        // 如果提交者存在，手机账户不存在
        else if (submitter != null && mobileUser == null) {
            // 提交者是管理员则以手机号新建一个用户
            // if (userService.isAdmin(submitter)) {
            // owner = userService.registerUserByMobile(record.getOwnerPhone());
            owner = submitter;

            // }else{
            // throw new StockException(ExceptionCode.OnlyAdminHelpToOnStock );
            // }
        }
        // 如果提交者不存在，手机账户存在，则以手机账户为准————目前不存在这种情况
        else if (submitter == null && mobileUser != null) {
            owner = mobileUser;
        }
        if (owner == null) {
            throw new StockException(ExceptionCode.NoOwnerWhenOnStock );
        }
        record.setOwnerUserId(owner.getId());
        Long id = onOffStockRecordMapper.add(record);
        if (record.getId() == null) {
            if (id == null) {
                return null;
            } else {
                record.setId(id);
            }
        }
        return record;
    }

    @Override
    public void offStock(OnOffStockRecord record) throws StockException {
        OnOffStockRecord exist = onOffStockRecordMapper.findOnOffStockRecordById(record.getId());
        // 如果未存在或者已下架
        if (exist == null) {
            throw new StockException(ExceptionCode.NoStockToOff );
        }
        // 如果处于借出状态
        if (exist.getBorrowStatus() != null && exist.getBorrowStatus() == BorrowStatus.BORROWED.getStatus()) {
            throw new StockException(ExceptionCode.ReturnItBeforeOffStock );
        }
        // 如果不是享书则无法下架
        if (exist.getBookType() != null && exist.getBookType() != BookType.SHARED.getType()) {
            throw new StockException(ExceptionCode.OnlySharedCanOffStock );
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
    public List<OnOffStockRecord> findOnOffStockRecordByBookIds(Long[] bookIds) {
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
