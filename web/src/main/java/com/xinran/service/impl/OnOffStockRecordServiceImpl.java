package com.xinran.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinran.constant.BookType;
import com.xinran.dao.mapper.OnOffStockRecordMapper;
import com.xinran.pojo.OnOffStockRecord;
import com.xinran.pojo.Pagination;
import com.xinran.service.OnOffStockRecordService;

/**
 * @author 高海军 帝奇 Apr 12, 2015 3:40:57 PM
 */
@Service
public class OnOffStockRecordServiceImpl implements OnOffStockRecordService {

    @Autowired
    private OnOffStockRecordMapper onOffStockRecordMapper;

    @Override
    public OnOffStockRecord onStock(OnOffStockRecord record) {
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
    public OnOffStockRecord findOnOffStockRecordById(Long id) {
        OnOffStockRecord record = onOffStockRecordMapper.findOnOffStockRecordById(id);
        return record;
    }

	@Override
	public List<OnOffStockRecord> findShared(Long userId, Pagination page) {
		if(userId == null){
			throw new IllegalArgumentException();
		}
		if(page == null){
			page = new Pagination();
		}
		OnOffStockRecord record = new OnOffStockRecord();
		record.setOwnerUserId(userId);
		record.setBookType(BookType.SHARED.getType());
		return onOffStockRecordMapper.findRecordsByUserId(record, page);
	}

	@Override
	public List<OnOffStockRecord> findDonated(Long userId, Pagination page) {
		if(userId == null){
			throw new IllegalArgumentException();
		}
		if(page == null){
			page = new Pagination();
		}
		OnOffStockRecord record = new OnOffStockRecord();
		record.setOwnerUserId(userId);
		record.setBookType(BookType.DONATED.getType());
		return onOffStockRecordMapper.findRecordsByUserId(record, page);
	}
}
