package com.xinran.controller.mobile;

import com.xinran.controller.common.AbstractActivityController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by gsy on 2015/11/22.
 */
@RestController(value = "mobileActivityController")
@RequestMapping("/mobile")
public class ActivityController extends AbstractActivityController {
}
