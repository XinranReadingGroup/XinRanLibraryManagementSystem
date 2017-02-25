package com.xinran.service.impl;

import com.xinran.constant.SystemResultCode;
import com.xinran.dao.mapper.QRCodeMapper;
import com.xinran.exception.BorrowOrReturnValidationException;
import com.xinran.pojo.QRCode;
import com.xinran.service.QRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author 高海军 帝奇 01.16, 2017 3:47:24 PM
 */
@Service
public class QRCodeServiceImpl implements QRCodeService {

    @Autowired
    private QRCodeMapper qrCodeMapper;

    @Override
    public void addQRCode(String text) {
        QRCode qrCode = new QRCode();
        qrCode.setStatus(QRCode.NOT_USERD);
        qrCode.setContent(text);
        qrCode.setType("bookQRCode");

        qrCodeMapper.addQRCode(qrCode);
    }

    @Override
    public QRCode associate(String content) {

        QRCode qrCode = qrCodeMapper.findQRCodeByContent(content);
        if (qrCode == null) {
            throw new BorrowOrReturnValidationException(SystemResultCode.NoPreAssignedQrCodeFound);

        } else {
            if (QRCode.USERD == qrCode.getStatus()) {
                throw new BorrowOrReturnValidationException(SystemResultCode.QrcodeAlReadyBeenTaken);
            }

            int updateCount = qrCodeMapper.associate(qrCode);

            if (0 == updateCount) {
                throw new BorrowOrReturnValidationException(SystemResultCode.QrcodeAlReadyBeenTaken);
            }

            return qrCode;

        }


    }

    @Override
    public QRCode findQRCodeById(Long id) {
        return qrCodeMapper.findQRCodeById(id);
    }

    @Override
    public QRCode findQRCodeByContent(String content){
        return qrCodeMapper.findQRCodeByContent(content);
    }


}
