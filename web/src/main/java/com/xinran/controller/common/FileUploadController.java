package com.xinran.controller.common;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.xinran.controller.util.UserIdenetityUtil;
import com.xinran.vo.AjaxResult;
import com.xinran.vo.builder.AjaxResultBuilder;

/**
 * @author 高海军 帝奇 May 31, 2015 11:18:06 AM
 */
@Controller
public class FileUploadController {

    private Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @RequestMapping(value = "/uploadFile", method = RequestMethod.GET)
    public ModelAndView toUploadForm(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("uploadFile");

    }

    @InitBinder("uploadItem")
    void initBinder(WebDataBinder binder) {
        binder.setValidator(new UploadValidator());
    }
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody AjaxResult upload(@Validated @ModelAttribute("uploadItem") FileUploadForm uploadForm,
                                         BindingResult result,
                       ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
        // String saveDirectory = request.getServletContext().getRealPath("/");

        MultipartFile multipartFile = uploadForm.getFile();
        String newFileName = "user_avatar_origin_" + UserIdenetityUtil.getCurrentUserId(request) + ".jpg";


        if (null != multipartFile) {
            System.out.println("12222222" + multipartFile.getContentType());

            try {
                multipartFile.transferTo(new File(newFileName));
            } catch (IllegalStateException | IOException e) {
                logger.error(e.getMessage(), e);
            }
                // multipartFile.
                // Handle file content - multipartFile.getInputStream()

        }

        // modelMap.addAttribute("files", multipartFile);
        return AjaxResultBuilder.buildSuccessfulResult(newFileName);
    }
}
