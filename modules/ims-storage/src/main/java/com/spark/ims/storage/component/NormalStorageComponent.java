package com.spark.ims.storage.component;

import com.spark.ims.common.constants.ErrorCode;
import com.spark.ims.core.component.ConfigurationParameter;
import com.spark.ims.core.exception.BusinessException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * Created by liyuan on 2018/4/26.
 */
@Component
public class NormalStorageComponent implements IStorageComponent{
    
    @Autowired
    protected ConfigurationParameter config;
    
    @Override
    public Long getFileLength(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            return file.length();
        }
        throw new BusinessException(ErrorCode.Common.fileNotFound);
    }
    
    @Override
    public boolean fileExist(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }
    
    @Override
    public Object upload(String fileName, String fileContent) throws IOException {
        File file = new File(fileName);
        file.getParentFile().mkdirs();
        file.createNewFile();
        try(
            FileOutputStream fileOutputStream = new FileOutputStream(file);)
        {
            fileOutputStream.write(fileContent.getBytes());
        }
        return file;
    }
    
    @Override
    public Object upload(String fileName, File file) {
        return file;
    }
    
    @Override
    public Object upload(String fileName, MultipartFile mFile) throws IOException {
        File file = new File(fileName);
        file.getParentFile().mkdirs();
        mFile.transferTo(file);
        file.createNewFile();
        return file;
    }
    
    @Override
    public Object upload(String fileName, InputStream file) {
        return null;
    }
    
    @Override
    public File download(String fileName) {
        return new File(fileName);
    }
    
    @Override
    public File download(String fileName, File file) {
        return file;
    }
    
    //@Override
    //public InputStream downloadAsStream(String fileName) throws IOException {
    //    File file = new File(fileName);
    //    try (InputStream inputStream = new FileInputStream(file)) {
    //        return inputStream;
    //    }
    //}
    
    @Override
    public void delete(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return;
        }
        File file = new File(fileName);
        // 删除服务器中的文件
        if (file.exists()) {
            file.delete();
        }
    }
    
    public static void main(String[] args) throws IOException {
        String str = "test";
        String path = "/Users/near/Desktop/test.txt";
        new NormalStorageComponent().upload(path,str);
        FileInputStream inputStream = new FileInputStream(path);
        System.out.println(IOUtils.toString(inputStream));
        
    }
}
