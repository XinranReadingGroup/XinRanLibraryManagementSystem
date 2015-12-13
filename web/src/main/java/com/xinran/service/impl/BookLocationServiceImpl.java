package com.xinran.service.impl;

import com.xinran.dao.mapper.BookLocationMapper;
import com.xinran.pojo.BookLocation;
import com.xinran.service.BookLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by zhuangyao.zy on 2015/5/24.
 */
@Service
public class BookLocationServiceImpl implements BookLocationService {
    @Autowired
    private BookLocationMapper locationMapper;

    @Override
    public BookLocation findBookLocationById(Long id) {
        return locationMapper.findBookLocationById(id);
    }

    @Override
    public List<BookLocation> query(String province, String city, String county) {
        return locationMapper.query(province, city, county);
    }

    @Override
    public List<BookLocation> queryAll() {
        return locationMapper.queryAll();
    }

    @Override
    public List<BookLocation> queryProvince() {
        return locationMapper.queryProvince();
    }

    @Override
    public List<BookLocation> queryCities(String province) {
        if (StringUtils.isEmpty(province)) {
            return null;
        }
        return locationMapper.queryCities(province);
    }

    @Override
    public List<BookLocation> queryCounties( String city) {
        if ( StringUtils.isEmpty(city)) {
            return null;
        }
        return locationMapper.queryCounties(city);
    }

    @Override
    public BookLocation add(BookLocation location) {
        locationMapper.add(location);
        return location;
    }

    @Override
    public int updateBookLocation(BookLocation location) {
        return locationMapper.update(location);
    }

    @Override
    public int delete(Long id) {
        return locationMapper.delete(id);
    }
}
