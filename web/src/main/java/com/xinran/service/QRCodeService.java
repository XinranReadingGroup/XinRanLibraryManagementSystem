package com.xinran.service;

import com.xinran.pojo.QRCode;
import com.xinran.pojo.Score;

/**
 * @author 高海军 帝奇 Apr 18, 2015 3:47:24 PM
 */
public interface QRCodeService {

    void addQRCode(String text);


    QRCode associate(String content);

    QRCode findQRCodeById(Long id);

}
