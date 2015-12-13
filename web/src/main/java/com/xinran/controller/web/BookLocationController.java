package com.xinran.controller.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


import org.springframework.web.bind.annotation.RestController;

import com.xinran.controller.common.AbstractBookLocationController;

/**
 * Created by zhuangyao.zy on 2015/5/24.
 */
@RestController(value = "webBookLocationController")
public class BookLocationController extends AbstractBookLocationController {

	@RequestMapping("/book/location/manager")
    public ModelAndView bookLocation(HttpServletRequest request) {
        return new ModelAndView("bookLocation");
    }


}