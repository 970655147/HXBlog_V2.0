package com.baidu.ueditor.utils;

import com.hx.log.util.Tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Jerry.X.He
 * @version 1.0
 * @TODO
 */
public final class FileUtils {

    // 初始化
    private FileUtils() {
        throw new RuntimeException("can't instantiate !");
    }

    /**
     * 根据给定的文件名,获取其后缀信息
     *
     * @param filename
     * @return
     */
    public static String getSuffixByFilename(String filename, String sep) {
        int lastIdxOf = filename.lastIndexOf(sep);
        if (lastIdxOf < 0) {
            return filename;
        }

        return filename.substring(lastIdxOf).toLowerCase();

    }

    public static String getFilenameByPath(String filename, String sep) {
        int lastIdxOf = filename.lastIndexOf(sep);
        if (lastIdxOf < 0) {
            return filename;
        }

        return filename.substring(lastIdxOf + 1).toLowerCase();

    }

    /**
     * 校验给定的类型是否在allowTypes中
     *
     * @param type
     * @param allowTypes
     * @return
     * @author Jerry.X.He
     */
    public static boolean validType(String type, String[] allowTypes) {
        List<String> list = Arrays.asList(allowTypes);

        return list.contains(type);
    }

    /**
     * 获取给定的path的绝对路径
     *
     * @param conf
     * @param path
     * @return
     * @author Jerry.X.He
     */
    public static String getPhysicalPath(Map<String, Object> conf, String path) {
        String rootPath = String.valueOf(conf.get(Constants.ROOT_PATH));
        return getPhysicalPath(rootPath, path);
    }

    /**
     * 根据给定的基路径 和相对路径, 获取物理路径
     *
     * @param baseDir
     * @param path
     * @return
     * @author Jerry.X.He
     */
    public static String getPhysicalPath(String baseDir, String path) {
        if (Tools.isEmpty(baseDir) || (Tools.isEmpty(path))) {
            throw new RuntimeException("path error !");
        }

        baseDir = baseDir.trim();
        path = path.trim();

        // addIfNotEndsWith(rootPath, "/") + removeIfStartsWith(path, "/")
        char baseDirLastCh = baseDir.charAt(baseDir.length() - 1);
        if (baseDirLastCh != '/' && baseDirLastCh != '\\') {
            baseDir += "/";
        }
        char relativeFirstCh = path.charAt(0);
        if (relativeFirstCh == '/' || relativeFirstCh == '\\') {
            path = path.substring(1);
        }

        return baseDir + path;
    }

    /**
     * 根据path解析相对路径
     * 从这里使用的角度来讲 : 依赖于于BinaryUploader返回的数据, 所以 当BinaryUploader返回的url有更新的时候, 这里需要级联更新
     * 1. ImageMultiDpiUploader.doExec 获取给定的path的相对路径
     * 2. ImageRetriver.doExec 获取给定的path的相对路径
     *
     * @param path
     * @return
     * @author Jerry.X.He
     */
    public static String resolveRleativePath(String path) {
        int lastIdxOfSlash = path.lastIndexOf('/');
        int lastIdxOfDot = path.lastIndexOf('.');
        if (lastIdxOfSlash < 0 || lastIdxOfDot < 0) {
            return null;
        }

        try {
            StringBuilder sb = new StringBuilder();
//			// -6 to trim random number
//			long fileCreateTs = Long.parseLong(path.substring(lastIdxOfSlash+1, lastIdxOfDot-6) );
//			Calendar cal = Calendar.getInstance();
//			cal.setTimeInMillis(fileCreateTs);
//			
//			// sb.append("/content");
//			sb.append("/" + fillIfNot(cal.get(Calendar.YEAR), 4) + fillIfNot((cal.get(Calendar.MONTH)+1), 2) + fillIfNot(cal.get(Calendar.DAY_OF_MONTH), 2) );
//			sb.append("/" + fillIfNot(cal.get(Calendar.HOUR_OF_DAY), 2) );
            sb.append(path.substring(lastIdxOfSlash));

            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将给定的val填充到fillTo位[填充0]
     *
     * @param val
     * @param fillTo
     * @return
     * @author Jerry.X.He
     */
    public static String fillIfNot(int val, int fillTo) {
        return String.format("%0" + fillTo + "d", val);
    }

    /**
     * 获取给定的path的 rootPath + DUMMY_PATH + path
     *
     * @param conf
     * @param path
     * @return
     * @author Jerry.X.He
     */
    public static String getPhysicalPathWithDummy(Map<String, Object> conf, String path) {
        return FileUtils.getPhysicalPath(FileUtils.getPhysicalPath(conf, Constants.DPI_DUMMY), path);
    }

    /**
     * 根据给定的dpi, 获取对应的dpi的文件夹
     *
     * @param dpi
     * @return
     * @author Jerry.X.He
     */
    public static String getDpiFolderName(int dpi) {
        return dpi + "X" + dpi;
    }

    /**
     * 如果给定的imgPath对应的路径不存在, 创建其父文件夹的所有文件
     *
     * @param imgPath
     * @author Jerry.X.He
     */
    public static void craeteFolderIfNotExists(String imgPath) {
        File imgParentFile = new File(imgPath).getParentFile();
        if (!imgParentFile.exists()) {
            imgParentFile.mkdirs();
        }
    }

    /**
     * 如果给定的的路径对应的文件, 删除该文件
     *
     * @param path
     * @author Jerry.X.He
     */
    public static void removeIfExists(String path) {
        try {
            Files.deleteIfExists(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
