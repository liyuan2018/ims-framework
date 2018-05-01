package com.spark.ims.storage.component;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by liyuan on 2018/4/26.
 */
 public interface IStorageComponent {
    
    boolean fileExist(String fileName);
    
    Long getFileLength(String fileName);
    
    Object upload(String fileName, String fileContent) throws IOException;
    
    Object upload(String fileName, File file) ;
    
    Object upload(String fileName, MultipartFile mFile) throws Exception;
    
    Object upload(String fileName, InputStream file);
    
    File download(String fileName);
    
    File download(String fileName, File file);
    
    void delete(String fileName);
    
   
    
    
}
