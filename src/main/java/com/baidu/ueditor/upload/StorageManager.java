package com.baidu.ueditor.upload;

import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;
import com.baidu.ueditor.utils.Constants;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class StorageManager {
    public static final int BUFFER_SIZE = 8192;

    public StorageManager() {
    }


    /**
     * 保存给定的字节数据到指定的文件
     *
     * @param data 给定的字节数据
     * @param path 需要保存的路径
     * @return com.baidu.ueditor.define.State
     * @author Jerry.X.He
     * @since 2017/1/24 11:09
     */
    public static State saveBinaryFile(byte[] data, String path) {
        File file = new File(path);
        State state = valid(file);

        if (!state.isSuccess()) {
            return state;
        }

        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bos.write(data);
            bos.flush();
            bos.close();
        } catch (IOException ioe) {
            return new BaseState(false, AppInfo.IO_ERROR);
        }

        state = new BaseState(true, file.getAbsolutePath());
        state.putInfo(Constants.SIZE, data.length);
//		state.putInfo( Constants.TITLE, file.getName() );
        return state;
    }


    /**
     * 根据给定的输入流, 保存图片
     *
     * @param is            输入流
     * @param path          需要保存的路径
     * @param maxResolution 最大可以保存的分辨率
     * @param maxSize       最大可以保存的文件大小
     * @return com.baidu.ueditor.define.State
     * @author Jerry.X.He
     * @since 2017/1/24 11:08
     */
    public static State saveImageByInputStream(InputStream is, String path, Float outputQuality, Integer maxResolution, long maxSize) {
        State state = null;
        File tmpFile = getTmpFile();

        try {
            String suffix = com.baidu.ueditor.utils.FileUtils.getSuffixByFilename(path, ".");
            BufferedImage image = ImageIO.read(is);
            long longger = Math.max(image.getWidth(), image.getHeight());
            if (maxResolution != null) {
                if (longger > maxResolution) {
                    image = Scalr.resize(image, maxResolution);
                }
            }
            Thumbnails.Builder<BufferedImage> builder = Thumbnails.of(image).scale(1.0f).outputFormat(suffix.substring(1));
            if (outputQuality != null) {
                builder.outputQuality(outputQuality);
            }
            builder.toFile(tmpFile);

            tmpFile = new File(tmpFile.getAbsolutePath() + suffix);
            if (tmpFile.length() > maxSize) {
                tmpFile.delete();
                return new BaseState(false, AppInfo.MAX_SIZE);
            }

            state = saveTmpFile(tmpFile, path);
            if (!state.isSuccess()) {
                tmpFile.delete();
            }
            return state;
        } catch (IOException e) {
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new BaseState(false, AppInfo.IO_ERROR);
    }

    /**
     * 从is中读取数据 保存到给定的文件中
     *
     * @param is      输入流
     * @param path    需要保存的路径
     * @param maxSize 最大可以保存的文件大小
     * @return com.baidu.ueditor.define.State
     * @author Jerry.X.He
     * @since 2017/1/24 11:10
     */
    public static State saveFileByInputStream(InputStream is, String path, long maxSize) {
        State state = null;
        File tmpFile = getTmpFile();

        byte[] dataBuf = new byte[2048];
        BufferedInputStream bis = new BufferedInputStream(is, StorageManager.BUFFER_SIZE);
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(tmpFile), StorageManager.BUFFER_SIZE);

            int count = 0;
            while ((count = bis.read(dataBuf)) != -1) {
                bos.write(dataBuf, 0, count);
            }
            bos.flush();
            bos.close();

            if (tmpFile.length() > maxSize) {
                tmpFile.delete();
                return new BaseState(false, AppInfo.MAX_SIZE);
            }

            state = saveTmpFile(tmpFile, path);
            if (!state.isSuccess()) {
                tmpFile.delete();
            }
            return state;
        } catch (IOException e) {
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new BaseState(false, AppInfo.IO_ERROR);
    }

    public static State saveFileByInputStream(InputStream is, String path) {
        return saveFileByInputStream(is, path, Long.MAX_VALUE);
    }


    /**
     * 获取一个临时文件
     *
     * @return java.io.File
     * @author Jerry.X.He
     * @since 2017/1/24 11:11
     */
    private static File getTmpFile() {
        File tmpDir = FileUtils.getTempDirectory();
        String tmpFileName = (Math.random() * 10000 + "").replace(".", "");
        return new File(tmpDir, tmpFileName);
    }

    /**
     * 将临时文件 "移动"到需要保存的文件路径
     *
     * @param tmpFile 给定的临时文件
     * @param path    需要保存的文件路径
     * @return com.baidu.ueditor.define.State
     * @author Jerry.X.He
     * @since 2017/1/24 11:12
     */
    private static State saveTmpFile(File tmpFile, String path) {
        State state = null;
        File targetFile = new File(path);

        if (targetFile.canWrite()) {
            return new BaseState(false, AppInfo.PERMISSION_DENIED);
        }
        try {
            FileUtils.moveFile(tmpFile, targetFile);
        } catch (IOException e) {
            return new BaseState(false, AppInfo.IO_ERROR);
        }

        state = new BaseState(true);
        state.putInfo(Constants.SIZE, targetFile.length());
//		state.putInfo( Constants.TITLE, targetFile.getName() );

        return state;
    }

    /**
     * 校验给定的文件, 是否能够向其写出数据
     *
     * @param file 给定的文件
     * @return com.baidu.ueditor.define.State
     * @author Jerry.X.He
     * @since 2017/1/24 11:13
     */
    private static State valid(File file) {
        File parentPath = file.getParentFile();

        if ((!parentPath.exists()) && (!parentPath.mkdirs())) {
            return new BaseState(false, AppInfo.FAILED_CREATE_FILE);
        }

        if (!parentPath.canWrite()) {
            return new BaseState(false, AppInfo.PERMISSION_DENIED);
        }

        return new BaseState(true);
    }
}
