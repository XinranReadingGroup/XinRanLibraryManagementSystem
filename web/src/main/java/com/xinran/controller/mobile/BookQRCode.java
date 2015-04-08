package com.xinran.controller.mobile;
// package com.xinran.controller;
//
// import java.io.ByteArrayOutputStream;
// import java.io.IOException;
// import java.io.OutputStream;
//
// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
//
// import org.springframework.stereotype.Controller;
//
// import com.google.zxing.qrcode.QRCode;
//
// @Controller
// public class BookQRCode {
//
// public void xx(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
// String qrtext = request.getParameter("qrtext");
//
// ByteArrayOutputStream out = QRCode.from(qrtext).to(ImageStorage.ImageType.PNG).stream();
//
// response.setContentType("image/png");
// response.setContentLength(out.size());
//
// OutputStream outStream = response.getOutputStream();
//
// outStream.write(out.toByteArray());
//
// outStream.flush();
// outStream.close();
// }
// }
