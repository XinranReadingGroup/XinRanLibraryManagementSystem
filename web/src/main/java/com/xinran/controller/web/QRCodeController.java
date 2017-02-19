package com.xinran.controller.web;

import com.xinran.constant.SystemConfig;
import com.xinran.controller.util.UserIdenetityUtil;
import com.xinran.exception.AdminAuthorizationException;
import com.xinran.pojo.Activity;
import com.xinran.pojo.User;
import com.xinran.service.QRCodeService;
import com.xinran.service.UserService;
import com.xinran.util.picture.PictureUtil;
import com.xinran.util.qrcode.QRCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by 高海军 帝奇 74394 on 2017 January  19:54.
 */
@RestController(value = "webQRCodeController")

public class QRCodeController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected UserService userService;

    @Autowired
    protected QRCodeService qrCodeService;

    //TUNE 可以通过对URL拦截判断。
    @RequestMapping("/admin/qrcode/generate/")
    public void generate(  HttpServletRequest request,HttpServletResponse response)  {
        User user = userService.findUserByUserId(UserIdenetityUtil.getCurrentUserId(request));
        if ( userService.isAdmin(user.getUserName())) {
            response.setContentType("image/png");


            try {


                int size = 16;
                List list = new ArrayList(size);
                for (int i = 0; i < size; i++) {
                    String uuid = UUID.randomUUID().toString();
                    String text = "v1:book:" + uuid;
                    String fileName = uuid + ".png";
                    list.add(SystemConfig.TEMP_IMG_DIR + fileName);
                    qrCodeService.addQRCode(text);
                    QRCodeUtil.createCode(text, SystemConfig.TEMP_IMG_DIR, fileName);

                }

                String filePathAndFileName = SystemConfig.TEMP_IMG_DIR + UUID.randomUUID().toString() + "_merged.png";
                PictureUtil.merge(list, filePathAndFileName);

                InputStream inputStream = new BufferedInputStream(new FileInputStream(filePathAndFileName));
                FileCopyUtils.copy(inputStream, response.getOutputStream());


            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

        }else{
            throw new AdminAuthorizationException();
        }
    }
}
