package com.xinran.controller.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.xinran.controller.common.AbstractBookLocationController;

/**
 * Created by zhuangyao.zy on 2015/5/24.
 */
@RestController(value = "webBookLocationController")
public class BookLocationController extends AbstractBookLocationController {

	@Override
    @RequestMapping("/book/location/manager")
    public ModelAndView bookLocation(HttpServletRequest request) {
        return new ModelAndView("bookLocation");
    }


}