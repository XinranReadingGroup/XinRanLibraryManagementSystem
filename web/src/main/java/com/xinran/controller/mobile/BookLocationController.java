package com.xinran.controller.mobile;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xinran.controller.common.AbstractBookLocationController;

/**
 * Created by zhuangyao.zy on 2015/5/24.
 */
@RestController(value = "mobileBookLocationController")
@RequestMapping("/mobile")
public class BookLocationController extends AbstractBookLocationController {


}