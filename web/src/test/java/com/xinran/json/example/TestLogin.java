package com.xinran.json.example;

import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.xinran.vo.AjaxResult;
import com.xinran.vo.builder.AjaxResultBuilder;

/**
 * @author 高海军 帝奇 Apr 9, 2015 8:19:59 PM
 */
public class TestLogin {

    @Test
    public void signInOrSignUp() {
        Map<String, String> jsonMap = Maps.newHashMapWithExpectedSize(1);
        jsonMap.put("accessToken", "testAccessToken");
        AjaxResult result = AjaxResultBuilder.buildSuccessfulResult(jsonMap);
        System.out.println(JSONObject.toJSONString(result));
    }

}
