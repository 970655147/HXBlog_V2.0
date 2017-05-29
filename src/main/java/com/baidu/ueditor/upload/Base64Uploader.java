package com.baidu.ueditor.upload;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;
import com.baidu.ueditor.utils.Constants;
import com.hx.log.alogrithm.code.Codec;

import java.util.Map;

public final class Base64Uploader {

    public static State save(String content, Map<String, Object> conf) {

        byte[] data = decode(content);
        long maxSize = ((Long) conf.get(Constants.MAX_SIZE)).longValue();

        if (!validSize(data, maxSize)) {
            return new BaseState(false, AppInfo.MAX_SIZE);
        }

        String suffix = FileType.getSuffix(FileType.JPG);

        String savePath = PathFormat.parse((String) conf.get(Constants.SAVE_PATH),
                (String) conf.get(Constants.FILE_NAME));

        savePath = savePath + suffix;
        String physicalPath = (String) conf.get(Constants.ROOT_PATH) + savePath;

        State storageState = StorageManager.saveBinaryFile(data, physicalPath);

        if (storageState.isSuccess()) {
            storageState.putInfo(Constants.URL, PathFormat.format(savePath));
            storageState.putInfo(Constants.TYPE, suffix);
            storageState.putInfo(Constants.ORIGINAL, "");
        }

        return storageState;
    }

    private static byte[] decode(String content) {
        return Codec.base64D(content.getBytes());
    }

    private static boolean validSize(byte[] data, long length) {
        return data.length <= length;
    }

}