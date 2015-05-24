package com.xinran.controller.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xinran.pojo.BookLocation;
import com.xinran.service.BookLocationService;
import com.xinran.vo.AjaxResult;
import com.xinran.vo.builder.AjaxResultBuilder;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhuangyao.zy on 2015/5/23.
 */
public class AbstractBookLocationController {

    @Autowired
    private BookLocationService locationService;

    @RequestMapping("/book/location/add")
    public
    @ResponseBody
    AjaxResult add(BookLocation location, HttpServletRequest request) {
        return AjaxResultBuilder.buildSuccessfulResult(locationService.add(location));
    }

    @RequestMapping("/book/location/query")
    public
    @ResponseBody
    AjaxResult query(@RequestParam(value="province", required = false) String province,
                     @RequestParam(value="city", required = false) String city,
                     @RequestParam(value="county", required = false) String county, HttpServletRequest request) {
        return AjaxResultBuilder.buildSuccessfulResult(locationService.query(province, city, county));
    }

    @RequestMapping("/book/location/provinces")
    public
    @ResponseBody
    AjaxResult provinces(HttpServletRequest request) {
        return AjaxResultBuilder.buildSuccessfulResult(new ArrayList<String>(data.keySet()));
    }

    @RequestMapping("/book/location/cities")
    public
    @ResponseBody
    AjaxResult cities(@RequestParam("province") String province, HttpServletRequest request) {
        Object cities = data.get(province);
        Map<String, Object> result = new HashMap<>();
        if (cities != null && cities instanceof List<?>) {
            result.put("type", "counties");
            result.put("counties", cities);
        } else if (cities != null && cities instanceof Map<?, ?>) {
            result.put("type", "cities");
            result.put("cities", ((Map<String, Object>) cities).keySet());
        }
        return AjaxResultBuilder.buildSuccessfulResult(result);
    }

    @RequestMapping("/book/location/counties")
    public
    @ResponseBody
    AjaxResult counties(@RequestParam("province") String province, @RequestParam("city") String city, HttpServletRequest request) {
        Object cities = data.get(province);
        Object result = null;
        if (cities != null && cities instanceof List<?>) {
            result = cities;

        } else if (cities != null && cities instanceof Map<?, ?>) {
            result = ((Map<String, Object>) cities).get(city);
        }
        return AjaxResultBuilder.buildSuccessfulResult(result);
    }

    /**
     * 暂时用JSON静态资源来描述省市区基本信息。
     */
    private final Map<String, Object> data = new HashMap<>();

    @PostConstruct
    public void init() throws IOException {
        Reader reader = new InputStreamReader(getClass().getResourceAsStream("/locations.json"), "UTF-8");
        char[] buffer = new char[128];
        int num = reader.read(buffer);
        StringBuilder jsonBuilder = new StringBuilder();
        while (num > 0) {
            jsonBuilder.append(buffer, 0, num);
            num = reader.read(buffer);
        }
        reader.close();
        JSONArray json = JSON.parseArray(jsonBuilder.toString());
        if (json == null || json.size() < 1) {
            return;
        }
        for (int i = 0; i < json.size(); i++) {
            JSONObject jsonProvince = json.getJSONObject(i);
            boolean isDirect = false;
            if (jsonProvince.containsKey("isDirect")) {
                isDirect = jsonProvince.getBoolean("isDirect");
            }
            String name = jsonProvince.getString("name");
            if (StringUtils.isBlank(name)) {
                continue;
            }
            if (isDirect) {
                List<String> counties = extractCounties(jsonProvince.getJSONArray("counties"));
                data.put(name, counties);
            } else {
                Map<String, List<String>> cities = extractCities(jsonProvince.getJSONArray("cities"));
                data.put(name, cities);
            }
        }
    }

    private List<String> extractCounties(JSONArray counties) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < counties.size(); i++) {
            String name = counties.getJSONObject(i).getString("name");
            if (StringUtils.isNotBlank(name)) {
                result.add(name);
            }
        }
        return result;
    }

    private Map<String, List<String>> extractCities(JSONArray cities) {
        if (cities == null || cities.size() < 1) {
            return null;
        }
        Map<String, List<String>> result = new HashMap<>();
        for (int i = 0; i < cities.size(); i++) {
            JSONObject city = cities.getJSONObject(i);
            String name = city.getString("name");
            if (StringUtils.isBlank(name)) {
                continue;
            }
            List<String> counties = extractCounties(city.getJSONArray("counties"));
            if (counties == null) {
                counties = new ArrayList<>(0);
            }
            result.put(name, counties);
        }
        return result;
    }

}
