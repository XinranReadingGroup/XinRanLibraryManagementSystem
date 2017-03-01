package com.xinran.util.picture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * COPY FROM http://www.bkjia.com/ASPjc/989270.html
 * <p> COPY FORM http://cxr1217.iteye.com/blog/1638681
 * <p> COPY FROM http://www.cnblogs.com/yzlpersonal/p/3656125.html
 * Created by 高海军 帝奇 74394 on 2017 January  11:53.
 */
public class PictureUtil {

    /**
     * 将宽度相同的图片，竖向追加在一起 ##注意：宽度必须相同
     *
     * @param piclist             文件路径列表
     * @param filePathAndFileName 输出路径
     */
    public static void mergeToOneColumn(List<String> piclist, String filePathAndFileName) {// 纵向处理图片

        try {
            int height = 0, // 总高度
                    width = 0, // 总宽度
                    _height = 0, // 临时的高度 , 或保存偏移高度
                    __height = 0, // 临时的高度，主要保存每个高度
                    picNum = piclist.size();// 图片的数量
            File fileImg = null; // 保存读取出的图片
            int[] heightArray = new int[picNum]; // 保存每个文件的高度
            BufferedImage buffer = null; // 保存图片流
            List<int[]> imgRGB = new ArrayList<int[]>(); // 保存所有的图片的RGB
            int[] _imgRGB; // 保存一张图片中的RGB数据
            for (int i = 0; i < picNum; i++) {
                fileImg = new File(piclist.get(i));
                buffer = ImageIO.read(fileImg);
                heightArray[i] = _height = buffer.getHeight();// 图片高度
                if (i == 0) {
                    width = buffer.getWidth();// 图片宽度
                }
                height += _height; // 获取总高度
                _imgRGB = new int[width * _height];// 从图片中读取RGB
                _imgRGB = buffer.getRGB(0, 0, width, _height, _imgRGB, 0, width);
                imgRGB.add(_imgRGB);
            }
            _height = 0; // 设置偏移高度为0
            // 生成新图片
            BufferedImage imageResult = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < picNum; i++) {
                __height = heightArray[i];
                if (i != 0) _height += __height; // 计算偏移高度
                imageResult.setRGB(0, _height, width, __height, imgRGB.get(i), 0, width); // 写入流中
            }
            File outFile = new File(filePathAndFileName);
            ImageIO.write(imageResult, "png", outFile);// 写图片
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void mergeToOneRow(List<String> piclist, String filePathAndFileName) {// 纵向处理图片
        int size = piclist.size();

        try {
            String firstFileName = piclist.get(0);
            BufferedImage firstBufferedImage = ImageIO.read(new File(firstFileName));
            int firstWidth = firstBufferedImage.getWidth();// 图片宽度
            int firstHeight = firstBufferedImage.getHeight();// 图片高度
            int[] firstImageArray = new int[firstWidth * firstHeight];// 从图片中读取RGB
            firstImageArray = firstBufferedImage.getRGB(0, 0, firstWidth, firstHeight, firstImageArray, 0, firstWidth);

            BufferedImage finalImageResult = new BufferedImage(firstWidth * size , firstHeight,BufferedImage.TYPE_INT_RGB);
            finalImageResult.setRGB(0, 0, firstWidth, firstHeight, firstImageArray, 0, firstWidth);// 设置左半部分的RGB

            for (int i = 1; i < size; i++) {
                String fileName = piclist.get(i);
                BufferedImage bufferedImage = ImageIO.read(new File(fileName));
                int width = bufferedImage.getWidth();// 图片宽度
                int height = bufferedImage.getHeight();// 图片高度
                int[] imageArray = new int[width * height];// 从图片中读取RGB
                imageArray = bufferedImage.getRGB(0, 0, width, height, imageArray, 0, width);

                //设置起始x
                finalImageResult.setRGB(width*i, 0, firstWidth, firstHeight, imageArray, 0, firstWidth);// 设置左半部分的RGB


            }

            File outFile = new File(filePathAndFileName);
            ImageIO.write(finalImageResult, "png", outFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
