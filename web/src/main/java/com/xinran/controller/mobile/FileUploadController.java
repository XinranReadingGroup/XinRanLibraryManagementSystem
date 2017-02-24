package com.xinran.controller.mobile;

import com.xinran.constant.SystemConfig;
import com.xinran.controller.common.AbstractFileUploadController;
import com.xinran.controller.common.FileUploadForm;
import com.xinran.controller.common.UploadValidator;
import com.xinran.controller.util.UserIdenetityUtil;
import com.xinran.vo.AjaxResult;
import com.xinran.vo.builder.AjaxResultBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * @author 高海军 帝奇 May 31, 2015 11:18:06 AM
 */
@RestController(value = "mobileFileUploadController")
@RequestMapping("/mobile")
public class FileUploadController extends AbstractFileUploadController{


}
