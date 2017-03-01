package com.xinran.controller.web;

import com.alibaba.fastjson.util.IOUtils;
import com.xinran.constant.SystemConfig;
import com.xinran.controller.util.UserIdenetityUtil;
import com.xinran.exception.AdminAuthorizationException;
import com.xinran.pojo.User;
import com.xinran.service.QRCodeService;
import com.xinran.service.UserService;
import com.xinran.util.picture.PictureUtil;
import com.xinran.util.qrcode.QRCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by 高海军 帝奇 74394 on 2017 January  19:54.
 */
@RestController(value = "webQRCodeController")

public class QRCodeController {

    private static final int ROW_SIZE = 5;

    private static final int COL_SIZE = 4;
    public static final int BUFFER_SIZE = 1024 * 128;


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

            InputStream inputStream = null;
            try {

                List<String> list = new ArrayList<>(COL_SIZE);
                for (int i = 0; i <COL_SIZE ; i++) {
                    String filePathAndFileName = SystemConfig.TEMP_IMG_DIR + UUID.randomUUID().toString() + "_merged"+i+".png";
                    mergePic(filePathAndFileName);
                    list.add(filePathAndFileName);
                }

                String finalFileName = SystemConfig.TEMP_IMG_DIR + UUID.randomUUID().toString() + "final_merged.png";
                PictureUtil.mergeToOneRow(list, finalFileName);


                inputStream = new BufferedInputStream(new FileInputStream(finalFileName), BUFFER_SIZE);
                FileCopyUtils.copy(inputStream, response.getOutputStream());


            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }finally {
                IOUtils.close(inputStream);
            }

        }else{
            throw new AdminAuthorizationException();
        }
    }

    private String mergePic(String filePathAndFileName) throws Exception {
        List<String> list = new ArrayList(ROW_SIZE);
        for (int i = 0; i < ROW_SIZE; i++) {
            String uuid = UUID.randomUUID().toString();
            String text = "v1:book:" + uuid;
            String fileName = uuid + ".png";
            list.add(SystemConfig.TEMP_IMG_DIR + fileName);
            qrCodeService.addQRCode(text);
            QRCodeUtil.createCode(text, SystemConfig.TEMP_IMG_DIR, fileName);

        }


        PictureUtil.mergeToOneColumn(list, filePathAndFileName);
        return filePathAndFileName;
    }
}
