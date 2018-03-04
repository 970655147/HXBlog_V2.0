package com.hx.blog_v2.domain.common.resources;

import com.hx.log.util.Tools;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemHeaders;

import java.io.*;

/**
 * 一个简易的 FileItem
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 7/2/2017 10:53 AM
 */
public class SimpleFileItem implements FileItem {

    private String fieldName;
    private boolean isFormField;
    private String contentType;
    private String fileName;
    private File file;
    private FileItemHeaders headers;
    private String destFile;

    public SimpleFileItem(String fieldName, boolean isFormField, String contentType,
                          String fileName, File file, String destFile) {
        this.fieldName = fieldName;
        this.isFormField = isFormField;
        this.contentType = contentType;
        this.fileName = fileName;
        this.file = file;
        this.destFile = destFile;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new BufferedInputStream(new FileInputStream(file));
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public String getName() {
        return fileName;
    }

    @Override
    public boolean isInMemory() {
        return false;
    }

    @Override
    public long getSize() {
        return file.length();
    }

    @Override
    public byte[] get() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream((int) getSize());
        try {
            Tools.copy(getInputStream(), baos);
        } catch (IOException e) {
            return new byte[]{};
        }
        return baos.toByteArray();
    }

    @Override
    public String getString(String encoding) throws UnsupportedEncodingException {
        try {
            return Tools.getContent(getInputStream(), encoding);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public String getString() {
        try {
            return Tools.getContent(getInputStream(), Tools.DEFAULT_CHARSET);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public void write(File file) throws Exception {
        Tools.copy(getInputStream(), new FileOutputStream(file));
    }

    @Override
    public void delete() {
        file.delete();
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public void setFieldName(String name) {
        fieldName = name;
    }

    @Override
    public boolean isFormField() {
        return isFormField;
    }

    @Override
    public void setFormField(boolean state) {
        isFormField = state;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return new BufferedOutputStream(new FileOutputStream(destFile));
    }

    @Override
    public FileItemHeaders getHeaders() {
        return headers;
    }

    @Override
    public void setHeaders(FileItemHeaders headers) {
        this.headers = headers;
    }
}
