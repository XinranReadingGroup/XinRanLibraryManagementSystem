package com.xinran.json.example;

import java.util.Date;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.xinran.pojo.Book;
import com.xinran.vo.AjaxResult;
import com.xinran.vo.builder.AjaxResultBuilder;

/**
 * @author 高海军 帝奇 Apr 9, 2015 8:19:59 PM
 */
public class TestLogin {

    @Test
    public void signInOrSignUp() {
        JSONObject obj = new JSONObject();
        obj.put("name", "[-echo-]");
        System.out.println(obj.toString()); // {"name":["-echo-"]}

        Map<String, String> jsonMap = Maps.newHashMapWithExpectedSize(1);
        jsonMap.put("accessToken", "testAccessToken");
        AjaxResult result = AjaxResultBuilder.buildSuccessfulResult(jsonMap);
        System.out.println(JSONObject.toJSONString(result));
    }

    @Test
    public void donate() {
        Book book = new Book();
        book.setAuthor("海子");
        book.setCreatedAt(new Date());
        book.setUpdatedAt(new Date());
        book.setId(1L);
        book.setImgUrl("http://img.url");
        book.setIsbn("123456Isbn");
        book.setSummary("呵呵呵");
        book.setTitle("面朝大海");
        AjaxResult result = AjaxResultBuilder.buildSuccessfulResult(book);
        System.out.println(JSONObject.toJSONString(result));
    }

    @Test
    public void borrowSuccess() {
        AjaxResult result = AjaxResultBuilder.buildSuccessfulResult(null);
        System.out.println(JSONObject.toJSONString(result));
    }

   

}
