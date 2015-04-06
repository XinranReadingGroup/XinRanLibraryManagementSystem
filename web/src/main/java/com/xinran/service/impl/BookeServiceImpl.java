/*
 * Copyright 1999-2014 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.xinran.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinran.dao.mapper.BookMapper;
import com.xinran.pojo.Book;
import com.xinran.service.BookService;

/**
 * @author 高海军 帝奇 Apr 6, 2015 7:21:46 PM
 */
@Service
public class BookeServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    public List<Book> findAllWithPagenate(int limit, int offset) {
        return bookMapper.findAllWithPagenate(limit, offset);
    }

}
