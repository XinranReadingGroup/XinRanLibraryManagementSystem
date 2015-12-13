package com.xinran.service;

import com.xinran.pojo.BookLocation;

import java.util.List;

/**
 * Created by zhuangyao.zy on 2015/5/24.
 */
public interface BookLocationService {

    BookLocation findBookLocationById(Long id);

    List<BookLocation> query(String province, String city, String county);

    List<BookLocation> queryAll();

    List<BookLocation> queryProvince();

    List<BookLocation> queryCities(String province);

    List<BookLocation> queryCounties(String city);

    BookLocation add(BookLocation location);

    int updateBookLocation(BookLocation location);

    int delete(Long id);
}
