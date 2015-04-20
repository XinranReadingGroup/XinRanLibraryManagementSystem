package com.xinran.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinran.dao.mapper.OnOffStockRecordMapper;
import com.xinran.pojo.OnOffStockRecord;
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

    /*
     * (non-Javadoc)
     * @see com.xinran.service.OnOffStockRecordService#updateOnOffStockRecord(com.xinran.pojo.OnOffStockRecord)
     */
    @Override
    public int updateOnOffStockRecord(OnOffStockRecord record) {
        return onOffStockRecordMapper.updateOnOffStockRecord(record);
    }
}
