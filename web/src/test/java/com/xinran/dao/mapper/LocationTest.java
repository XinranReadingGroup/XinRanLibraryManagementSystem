package com.xinran.dao.mapper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.xinran.vo.LocationMeta;

/**
 * @author 高海军 帝奇 Apr 11, 2015 11:28:57 AM
 */
//如果在类上面使用该注解，这样所有的测试方案都会自动的 rollback
//@Transactional
// 再注意saveEmployee方法上的两个注解：
//这个注解表示使用事务
//这个表示方法执行完以后回滚事务，如果设置为false，则不回滚
public class LocationTest {



    @Test
    public void testAddUserAndFindById_Mail_Mobile_UserName() throws IOException {
        Reader reader = new InputStreamReader(getClass().getResourceAsStream("/locations.json"), "UTF-8");
        String str = IOUtils.toString(reader);
        List<LocationMeta> list = JSON.parseArray(str, LocationMeta.class);
        Assert.notNull(list);


    }



 
}
