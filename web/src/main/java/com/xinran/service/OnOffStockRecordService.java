package com.xinran.service;

import com.xinran.pojo.OnOffStockRecord;

/**
 * @author 高海军 帝奇 Apr 12, 2015 3:40:26 PM
 */
public interface OnOffStockRecordService {

    /**
     * 书本上架。目前由捐书和享书提供书本来源。
     * 
     * @return 如果上架成功，则返回完整记录，否则返回NULL
     */
    OnOffStockRecord onStock(OnOffStockRecord record);
    
    OnOffStockRecord findOnOffStockRecordById(Long id);

    int updateOnOffStockRecord(OnOffStockRecord record);

}
