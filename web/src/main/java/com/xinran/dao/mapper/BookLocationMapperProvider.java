package com.xinran.dao.mapper;

import java.util.Map;

/**
 * Created by zhuangyao.zy on 2015/5/24.
 */
public class BookLocationMapperProvider {

    public String genQuerySql(Map<String, Object> params) {
        StringBuilder buffer = new StringBuilder(32);
        buffer.append("SELECT * FROM book_location WHERE 1=1 ");
        if (params.get("province") != null) {
            buffer.append(" and province = #{province}");
        }
        if (params.get("city") != null) {
            buffer.append(" and city = #{city}");
        }
        if (params.get("county") != null) {
            buffer.append(" and county = #{county}");
        }
        return buffer.toString();
    }
}
