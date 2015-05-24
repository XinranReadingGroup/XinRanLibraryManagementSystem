package com.xinran.service;

import com.xinran.pojo.BookLocation;

import java.util.List;

/**
 * Created by zhuangyao.zy on 2015/5/24.
 */
public interface BookLocationService {

    List<BookLocation> query(String province, String city, String county);

    BookLocation add(BookLocation location);
}
