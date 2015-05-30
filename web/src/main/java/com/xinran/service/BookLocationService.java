package com.xinran.service;

import java.util.List;

import com.xinran.pojo.BookLocation;

/**
 * Created by zhuangyao.zy on 2015/5/24.
 */
public interface BookLocationService {

    BookLocation findBookLocationById(Long id);

    List<BookLocation> query(String province, String city, String county);

    BookLocation add(BookLocation location);
}
