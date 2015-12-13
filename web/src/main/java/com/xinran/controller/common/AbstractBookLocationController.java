package com.xinran.controller.common;

import com.alibaba.fastjson.JSON;
import com.xinran.controller.util.UserIdenetityUtil;
import com.xinran.pojo.BookLocation;
import com.xinran.service.BookLocationService;
import com.xinran.vo.AjaxResult;
import com.xinran.vo.LocationMeta;
import com.xinran.vo.builder.AjaxResultBuilder;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
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
    private BookLocationService                        locationService;

    /**
     * 暂时用JSON静态资源来描述省市区基本信息。
     */
    private final static Map<Long, String>             map          = new HashMap<Long, String>(64);            // id/name
    private final static List<LocationMeta>            provinceList = new ArrayList<LocationMeta>(32);
    private final static Map<Long, List<LocationMeta>> cityMap      = new HashMap<Long, List<LocationMeta>>(32);
    private final static Map<Long, List<LocationMeta>> countyMap    = new HashMap<Long, List<LocationMeta>>(32);

    @RequestMapping("/book/address/add")
    // 该权限需要被登录校验,所以改了个名字.
    public @ResponseBody AjaxResult add(BookLocation location, HttpServletRequest request) {
        Long currentUserId = UserIdenetityUtil.getCurrentUserId(request);

        location.setUserId(currentUserId);
        BookLocation add = locationService.add(location);
        return AjaxResultBuilder.buildSuccessfulResult(add);
    }

    @RequestMapping("/book/address/query")
    public @ResponseBody AjaxResult query(HttpServletRequest request) {
        List<BookLocation> result = locationService.queryAll();
        return AjaxResultBuilder.buildSuccessfulResult(result);
    }

    @RequestMapping("/book/address/update")
    public @ResponseBody AjaxResult update(HttpServletRequest request,BookLocation location) {
        int result = locationService.updateBookLocation(location);
        return AjaxResultBuilder.buildSuccessfulResult(result);
    }

    @RequestMapping("/book/address/delete")
    public @ResponseBody AjaxResult delete(HttpServletRequest request,long id) {
        int result = locationService.delete(id);
        return AjaxResultBuilder.buildSuccessfulResult(result);
    }

    @RequestMapping("/book/location/query")
    public @ResponseBody AjaxResult query(@RequestParam(value = "province", required = false) String province,
                                          @RequestParam(value = "city", required = false) String city,
                                          @RequestParam(value = "county", required = false) String county,
                                          HttpServletRequest request) {
        List<BookLocation> query = locationService.query(province, city, county);
        return AjaxResultBuilder.buildSuccessfulResult(query);
    }



    @RequestMapping("/book/location/provinces")
    public @ResponseBody AjaxResult provinces(HttpServletRequest request) {
        return AjaxResultBuilder.buildSuccessfulResult(locationService.queryProvince());
    }

    @RequestMapping("/book/location/provinces/{province}/cities")
    public @ResponseBody AjaxResult cities(@PathVariable(value = "province") String province, HttpServletRequest request) {
        return AjaxResultBuilder.buildSuccessfulResult(locationService.queryCities(province));
    }

    @RequestMapping("/book/location/cities/{city}/counties")
    public @ResponseBody AjaxResult counties(@PathVariable(value = "city") String city,
                                             HttpServletRequest request) {
        return AjaxResultBuilder.buildSuccessfulResult(locationService.queryCounties(city));
    }

    @RequestMapping("/book/location/new")
    public ModelAndView bookLocation(HttpServletRequest request) {
        return new ModelAndView("bookLocation");
    }

    /**
     * 
     */
    @PostConstruct
    public void init() {

        Reader reader = null;
        try {
            reader = new InputStreamReader(getClass().getResourceAsStream("/locations.json"), "UTF-8");
            String jsonText = IOUtils.toString(reader);
            List<LocationMeta> list = JSON.parseArray(jsonText, LocationMeta.class);
            for (LocationMeta originalLocationMeta : list) {
                LocationMeta locationMeta = new LocationMeta();
                locationMeta.setId(originalLocationMeta.getId());
                locationMeta.setName(originalLocationMeta.getName());
                locationMeta.setSub(originalLocationMeta.getSub());
                map.put(originalLocationMeta.getId(), originalLocationMeta.getName());
                provinceList.add(locationMeta);
            }

            for (LocationMeta provinceLocationMeta : provinceList) {
                List<LocationMeta> citiesList = provinceLocationMeta.getSub();
                cityMap.put(provinceLocationMeta.getId(), citiesList);

                for (LocationMeta cityLocationMeta : citiesList) {
                    List<LocationMeta> countyList = cityLocationMeta.getSub();
                    map.put(cityLocationMeta.getId(), cityLocationMeta.getName());

                    for (LocationMeta locationMeta : countyList) {
                        map.put(locationMeta.getId(), locationMeta.getName());
                    }

                    countyMap.put(cityLocationMeta.getId(), countyList);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Map<Long, String> getMap() {
        return map;
    }

    // private List<String> extractCounties(JSONArray counties) {
    // List<String> result = new ArrayList<>();
    // for (int i = 0; i < counties.size(); i++) {
    // String name = counties.getJSONObject(i).getString("name");
    // if (StringUtils.isNotBlank(name)) {
    // result.add(name);
    // }
    // }
    // return result;
    // }
    //
    // private Map<String, List<String>> extractCities(JSONArray cities) {
    // if (cities == null || cities.size() < 1) {
    // return null;
    // }
    // Map<String, List<String>> result = new HashMap<>();
    // for (int i = 0; i < cities.size(); i++) {
    // JSONObject city = cities.getJSONObject(i);
    // String name = city.getString("name");
    // if (StringUtils.isBlank(name)) {
    // continue;
    // }
    // List<String> counties = extractCounties(city.getJSONArray("counties"));
    // if (counties == null) {
    // counties = new ArrayList<>(0);
    // }
    // result.put(name, counties);
    // }
    // return result;
    // }

}
