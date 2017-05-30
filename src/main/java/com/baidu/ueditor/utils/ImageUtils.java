package com.baidu.ueditor.utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;

/**
 * 图片工具类
 *
 * @author Jerry.X.He
 * @version 1.0
 * @date 2017-01-10 15:42:24
 */
public final class ImageUtils {

    // 初始化
    private ImageUtils() {
        throw new RuntimeException("can't instantiate !");
    }


    /**
     * 图像切割(按指定起点坐标和宽高切割)
     *
     * @param srcImage 源图像
     * @param x        目标切片起点坐标X
     * @param y        目标切片起点坐标Y
     * @param width    目标切片宽度
     * @param height   目标切片高度
     */
    public static BufferedImage cut(BufferedImage srcImage, String type, int x, int y, int width, int height) {
        if(srcImage == null) {
            return null;
        }

        try {
            int srcWidth = srcImage.getHeight();
            int srcHeight = srcImage.getWidth();

            if (srcWidth > 0 && srcHeight > 0) {
//                Image image = srcImage.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
                Image image = srcImage;
                // 四个参数分别为图像起点坐标和宽高
                // 即: CropImageFilter(int x,int y,int width,int height)
                ImageFilter cropFilter = new CropImageFilter(x, y, width, height);
                Image img = Toolkit.getDefaultToolkit().createImage(
                        new FilteredImageSource(image.getSource(), cropFilter));

                BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = result.createGraphics();
                if (".png".equals(type)) {
                    result = g.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
                    g = result.createGraphics();
                }
                g.drawImage(img, 0, 0, width, height, null);
                g.dispose();

                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * resize给定的图片
     *
     * @param srcImage 原图像
     * @param type     图片的类型
     * @param width    resize之后的宽
     * @param height   resize之后的高
     * @return java.awt.image.BufferedImage
     * @author Jerry.X.He
     * @since 2017/1/23 17:14
     */
    public static BufferedImage resize(BufferedImage srcImage, String type, int width, int height) {
        if(srcImage == null) {
            return null;
        }

        try {
            BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = result.createGraphics();
            if (".png".equals(type)) {
                result = g.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
                g = result.createGraphics();
            }
            g.drawImage(srcImage, 0, 0, width, height, null);
            g.dispose();

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 将给定宽高的图片 剪切成正方形
     * 计算较长的一边需要crop掉的长度
     *
     * @param width  宽
     * @param height 高
     * @return
     * @author Jerry.X.He
     */
    public static Point calcNeedCropFrom(int width, int height) {
        int diff = width - height;
        if (diff < 0) {
            diff = -diff;
        }

        int halfDiff = (diff >> 1);
        if (width > height) {
            return new Point(halfDiff, 0);
        } else {
            return new Point(0, halfDiff);
        }
    }


    /**
     * 计算水印在给定的图片的中的位置 [这里暂时 算中间的位置]
     *
     * @param saveImage 给定的图片
     * @param logImage  给定的logo
     * @return
     * @author Jerry.X.He
     */
    public static Point calcWatermarkLeftUp(BufferedImage saveImage, BufferedImage logImage) {
        return new Point(halfOf(saveImage.getWidth() - logImage.getWidth()),
                halfOf(saveImage.getHeight() - logImage.getHeight()));
    }


    // 计算给定的整数的 1 / 2
    public static int halfOf(int original) {
        return original >> 1;
    }


}
