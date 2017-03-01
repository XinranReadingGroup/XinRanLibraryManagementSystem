package com.xinran.util.qrcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * Refactor from http://blog.csdn.net/gao36951/article/details/41148075#t2
 *
 * @Description: (普通二维码生成)
 * @author：Relieved
 * @date：2014-11-7 下午04:42:35
 */
public class QRCodeUtil {

    private static final MatrixToImageConfig DEFAULT_CONFIG = new MatrixToImageConfig();
    public static final int WIDTH_HEIGHT = 200;


    /**
     * 二维码的生成
     */
    public static void createCode(String text,  OutputStream output) throws Exception {
        int width = WIDTH_HEIGHT;
        int height = WIDTH_HEIGHT;
        // 二维码的图片格式
        String format = "png";
        /**
         * 设置二维码的参数
         */
        HashMap hints = new HashMap();
        // 内容所使用编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
        // 生成二维码
//        MatrixToImageWriter.writeToStream(bitMatrix, format, os);

        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix, DEFAULT_CONFIG);

        ImageOutputStream stream = null;
        try {
            stream = ImageIO.createImageOutputStream(output);
        } catch (IOException e) {
            throw new IIOException("Can't create output stream!", e);
        }


        ImageWriter writer = getWriter(image, format);
        doWrite(image, writer, stream);

//
//        if (!ImageIO.write(image, format, output)) {
//            throw new IOException("Could not write an image of format " + format);
//        }
    }

    /**
     * Returns <code>ImageWriter</code> instance according to given
     * rendered image and image format or <code>null</code> if there
     * is no appropriate writer.
     */
    private static ImageWriter getWriter(RenderedImage im,
                                         String formatName) {
        ImageTypeSpecifier type =
                ImageTypeSpecifier.createFromRenderedImage(im);
        Iterator<ImageWriter> iter = ImageIO.getImageWriters(type, formatName);

        if (iter.hasNext()) {
            return iter.next();
        } else {
            return null;
        }
    }

    /**
     * Writes image to output stream  using given image writer.
     */
    private static boolean doWrite(RenderedImage im, ImageWriter writer,
                                   ImageOutputStream output) throws IOException {
        if (writer == null) {
            return false;
        }
        writer.setOutput(output);
        try {
            writer.write(im);
        } finally {
//            writer.dispose();
//            output.flush();
        }
        return true;
    }


    /**
     * 二维码的生成
     */
    public static void createCode(String text, String filePath, String fileName) throws Exception {
        int width = WIDTH_HEIGHT;
        int height = WIDTH_HEIGHT;
        // 二维码的图片格式
        String format = "png";
        /**
         * 设置二维码的参数
         */
        HashMap hints = new HashMap();
        // 内容所使用编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
        // 生成二维码
        File outputFile = new File(filePath + fileName );
        MatrixToImageWriter.writeToPath(bitMatrix, format, outputFile.toPath());


    }

    /**
     * 二维码的解析
     *
     * @param file
     */
    public void parseCode(File file) {
        try {
            MultiFormatReader formatReader = new MultiFormatReader();

            if (!file.exists()) {
                return;
            }

            BufferedImage image = ImageIO.read(file);

            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);

            Map hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            Result result = formatReader.decode(binaryBitmap, hints);

            System.out.println("解析结果 = " + result.toString());
            System.out.println("二维码格式类型 = " + result.getBarcodeFormat());
            System.out.println("二维码文本内容 = " + result.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
