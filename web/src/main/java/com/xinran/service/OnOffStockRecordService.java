package com.xinran.service;

import java.util.List;

import com.xinran.pojo.OnOffStockRecord;
import com.xinran.pojo.Pagination;

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
    
    /**
     * 获取用户的享书记录(含书本信息)。
     * 
     * 查询条件分为两部分，一个为业务条件，一个为分页数据。
     * 业务条件中目前只支持UserId.
     * 分页数据如果为空，则使用默认数据，第一页，每页20.
     * 
     * @param userId 用户Id
     * @param page 分页
     * @return
     */
    List<OnOffStockRecord> findShared(Long userId, Pagination page);
    
    /**
     *  获取用户的享书记录(含书本信息)。
     * 
     * 查询条件分为两部分，一个为业务条件，一个为分页数据。
     * 业务条件中目前只支持UserId.
     * 分页数据如果为空，则使用默认数据，第一页，每页20.
     * 
     * @param userId 用户Id
     * @param page 分页
     * @return
     */
    List<OnOffStockRecord> findDonated(Long userId, Pagination page);
}
