package com.xinran.service.impl;

import com.xinran.dao.mapper.BookLocationMapper;
import com.xinran.pojo.BookLocation;
import com.xinran.service.BookLocationService;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public BookLocation add(BookLocation location) {
        Long id = locationMapper.add(location);
        if (id != null) {
            location.setId(id);
            return location;
        }else{
            return null;
        }
    }
}
