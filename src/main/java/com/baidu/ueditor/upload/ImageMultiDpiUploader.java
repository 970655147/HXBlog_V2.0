/**
 * file name : ImageMultiDpiUploader.java
 * created at : 22:07:39 2016-10-31
 * created by 970655147
 */

package com.baidu.ueditor.upload;

import com.baidu.ueditor.define.State;
import com.baidu.ueditor.utils.Constants;
import com.baidu.ueditor.utils.FileUtils;
import com.baidu.ueditor.utils.ImageUtils;
import com.baidu.ueditor.utils.MarkImageUtils;
import com.hx.json.JSONArray;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * 上传给定的图片, 并添加相关规格的图片, 以及需要添加水印的图片
 *
 * @author Jerry.X.He
 */
public class ImageMultiDpiUploader {
    /**
     * request, 以及当前上传任务的config
     */
    private HttpServletRequest request = null;
    private Map<String, Object> conf = null;


    public ImageMultiDpiUploader(HttpServletRequest request, Map<String, Object> conf) {
        this.request = request;
        this.conf = conf;
    }

    /**
     * 上传给定的文件
     * 然后创建不同分辨率的副本, 以及需要水印的图片 [后者可以异步执行]
     *
     * @return
     * @author Jerry.X.He
     */
    public final State doExec() {
        State state = BinaryUploader.save(this.request, this.conf);

        if (state.isSuccess()) {
            if (request.getParameter("ms") != null) {
                try {
                    String path = state.getInfo(Constants.URL);
                    // case 02 : resolve if return "http://image.jetmall.com/fileName.png"
                    // case 03. "ts.png"
                    path = FileUtils.resolveRleativePath(path);
                    if (path == null) {
                        return state;
                    }

                    String physicalPath = FileUtils.getPhysicalPath(this.conf, path);
                    String dummyPath = FileUtils.getPhysicalPathWithDummy(this.conf, path);
                    String type = state.getInfo(Constants.TYPE);

                    BufferedImage savedImage = ImageIO.read(new File(physicalPath));
                    // if draw to square ??
                    savedImage = generateProtoImage(savedImage);

                    // 绘制各个制定的分辨率大小的图片
                    JSONArray dpies = JSONArray.fromObject(String.valueOf(this.conf.get(Constants.DPIES)));
                    if (dpies != null) {
                        try {
                            String typeWithoutDot = type.substring(1);
                            for (Object _dpi : dpies) {
                                int dpi = Integer.parseInt(String.valueOf(_dpi));
                                String imgPath = dummyPath.replaceFirst(Constants.DPI_DUMMY, FileUtils.getDpiFolderName(dpi));
                                FileUtils.craeteFolderIfNotExists(imgPath);

                                // thumbnails
                                //			                Thumbnails.of(savedImage).size(200, 200)
                                //			                        .outputQuality(0.9f)
                                //			                        .outputFormat(typeWithoutDot).toFile(imgPath);

                                // ImageScalr
                                BufferedImage resizedImage = Scalr.resize(savedImage, dpi);
                                ImageIO.write(resizedImage, typeWithoutDot, new File(imgPath));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    // 绘制需要添加水印的各个分辨率的图片
                    JSONArray watermarkDpies = JSONArray.fromObject(String.valueOf(this.conf.get(Constants.WATERMARK_DPIES)));
                    if (watermarkDpies != null) {
                        try {
                            String typeWithoutDot = type.substring(1);
                            BufferedImage logImg = ImageIO.read(new File(FileUtils.getPhysicalPath(this.conf, Constants.LOG_FILE)));
                            for (Object _dpi : watermarkDpies) {
                                int dpi = Integer.parseInt(String.valueOf(_dpi));
                                String imgPath = dummyPath.replaceFirst(Constants.DPI_DUMMY, FileUtils.getDpiFolderName(dpi));
                                FileUtils.craeteFolderIfNotExists(imgPath);

                                // Thumbnails
                                //				        	Thumbnails.Builder thumbnails = Thumbnails.of(savedImage).size(dpi, dpi);
                                //				        	File waterFilePath = new File(FileType.getPhysicalPath(this.conf, LOG_FILE) );
                                //				        	if (waterFilePath.exists()) {
                                //				        		BufferedImage watermarkImage = ImageIO.read(waterFilePath);
                                //				        		thumbnails.watermark(Positions.CENTER, watermarkImage, 0.5f);
                                //				        	}
                                //				        	thumbnails.imageType(BufferedImage.TYPE_INT_ARGB);
                                //				        	thumbnails.outputQuality(0.95f);
                                //							thumbnails.outputFormat(typeWithoutDot).toFile(imgPath);

                                // ImageScalr + ImageUtils
                                BufferedImage resizedImage = Scalr.resize(savedImage, dpi);
                                Point leftUp = ImageUtils.calcWatermarkLeftUp(resizedImage, logImg);
                                MarkImageUtils.markImageByIcon(logImg, resizedImage, imgPath, null, leftUp.x, leftUp.y, logImg.getWidth(), logImg.getHeight());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return state;
    }

    // ---------------------------------------------------------- assist methods ----------------------------------------------

    /**
     * 根据给定的img, 创建以宽高较大者为标准, 创建BufferedImage
     *
     * @param savedImage 给定的img
     * @return
     * @author Jerry.X.He
     */
    private static BufferedImage generateProtoImage(BufferedImage savedImage) {
        int width = savedImage.getWidth();
        int height = savedImage.getHeight();
        int max = Math.max(width, height);// 最宽尺寸
        BufferedImage protoImage = new BufferedImage(max, max, savedImage.getType());
        Graphics2D g = (Graphics2D) protoImage.getGraphics();
        g.setBackground(Color.WHITE);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, max, max);
        // 计算绘制位置
        if (width == max) { // 上下居中
            g.drawImage(savedImage, 0, (height + max) / 2 - height, Color.WHITE, null);
        } else {
            g.drawImage(savedImage, (width + max) / 2 - width, 0, Color.WHITE, null);
        }
        g.dispose();

        return protoImage;
    }


}
