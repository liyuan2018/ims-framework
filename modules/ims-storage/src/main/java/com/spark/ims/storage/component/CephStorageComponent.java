package com.spark.ims.storage.component;

import com.alibaba.fastjson.JSONObject;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.spark.ims.common.constants.ErrorCode;
import com.spark.ims.common.domain.ResultData;
import com.spark.ims.core.component.ConfigurationParameter;
import com.spark.ims.core.exception.BusinessException;
import com.spark.ims.storage.constants.ParameterConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by liyuan on 2018/4/26.
 */
@Component
public class CephStorageComponent implements IStorageComponent{
    
    @Autowired
    protected ConfigurationParameter config;
    
    private static AmazonS3 s3Client = null;
    
    private  AmazonS3 getClient() {//读取配置
        if (s3Client == null) {
            synchronized (AmazonS3.class) {
                if (s3Client == null) {
                    String ak = config.getProperty(ParameterConstants.Ceph.accessKey, true);
                    String sk = config.getProperty(ParameterConstants.Ceph.secretKey, true);
                    String host = config.getProperty(ParameterConstants.Ceph.host, true);
                    String region = config.getProperty(ParameterConstants.Ceph.region, false);
                    Protocol protocol = host.contains("https") ? Protocol.HTTPS : Protocol.HTTP;
    
                    AWSCredentials credentials = new BasicAWSCredentials(ak, sk);
                    ClientConfiguration clientConfig = new ClientConfiguration();
                    clientConfig.setProtocol(protocol);
                    clientConfig.setSignerOverride("S3SignerType");//临时解决报"XAmzContentSHA256Mismatch"问题
    
                    AwsClientBuilder.EndpointConfiguration endPointConfig = new AwsClientBuilder.EndpointConfiguration(host, region);
                    s3Client = AmazonS3ClientBuilder.standard()
                        .withCredentials(new AWSStaticCredentialsProvider(credentials))
                        .withClientConfiguration(clientConfig)
                        .withEndpointConfiguration(endPointConfig).build();
                }
            }
        }
        return s3Client;
    }
    
    private String getBucketName(){
        return config.getProperty(ParameterConstants.Ceph.bucketName, true);
    }
    
    private String removeFirstSlash(String fileName){
        if (fileName.startsWith("/")) {
            fileName = fileName.replaceFirst("/", "");
            return fileName;
        }
        return fileName;
    }
    
    //配置变更,关闭client,重新初始化client
    public void refreshClientConfig(){
        getClient().shutdown();
        s3Client = null;
        getClient();
    }
    
    public ResultData listObjects(String bucketName) {
        JSONObject jsonObject = new JSONObject();
        ObjectListing listing = getClient().listObjects(bucketName);
        if (listing != null) {
            List<S3ObjectSummary> list = listing.getObjectSummaries();
            for (S3ObjectSummary s3ObjectSummary : list) {
                jsonObject.put(s3ObjectSummary.getKey(), s3ObjectSummary);
            }
        }
        ResultData resultData = new ResultData();
        resultData.put("result", jsonObject);
        return resultData;
    }

    private PutObjectResult upload(String bucketName, String fileName, File file){
        return getClient().putObject(bucketName, removeFirstSlash(fileName), file);
    }
    private PutObjectResult upload(String bucketName, String fileName, MultipartFile mFile) throws IOException {
        File file = new File(fileName);
        file.getParentFile().mkdirs();
        mFile.transferTo(file);
        return getClient().putObject(bucketName, removeFirstSlash(fileName), file);
    }
    
    @Override
    public Long getFileLength(String fileName) {
        ObjectMetadata objectMetadata = getClient().getObjectMetadata(getBucketName(),removeFirstSlash(fileName));
        if (objectMetadata != null) {
            return objectMetadata.getContentLength();
        }
        throw new BusinessException(ErrorCode.Common.fileNotFound);
    }
    
    @Override
    public boolean fileExist(String fileName) {
        return getClient().doesObjectExist(getBucketName(), removeFirstSlash(fileName));
    }
    
    @Override
    public Object upload(String fileName, String fileContent) {
        return getClient().putObject(getBucketName(),removeFirstSlash(fileName),fileContent);
    }
    
    /**
     * 文件上传
     * @param fileName
     * @param file
     * @return
     */
    @Override
    public Object upload(String fileName, File file){
        return upload(getBucketName(), removeFirstSlash(fileName), file);
    }
    
    @Override
    public Object upload(String fileName, MultipartFile mFile) throws IOException {
        return upload(getBucketName(), fileName, mFile);
    }
    
    private PutObjectResult upload(String bucketName, String fileName, InputStream inputStream){
        return getClient().putObject(bucketName, removeFirstSlash(fileName), inputStream, new ObjectMetadata());
    }

    /**
     * 文件上传
     * @param fileName
     * @param inputStream
     * @return
     */
    @Override
    public PutObjectResult upload(String fileName, InputStream inputStream){
        fileName = removeFirstSlash(fileName);
        return upload(getBucketName(), fileName, inputStream);
    }

    /**
     * 文件下载
     * @param fileName
     * @return
     */
    @Override
    public File download(String fileName){
        File file = new File(fileName);
        return  download(removeFirstSlash(fileName), file);
    }
    
    /**
     * 文件下载
     * @param fileName
     * @return
     */
    @Override
    public File download(String fileName,File file){
        ObjectMetadata metadata = getClient().getObject(new GetObjectRequest(getBucketName(), removeFirstSlash(fileName)), file);
        if (metadata != null) {
            return file;
        }
        return null;
    }
    
    //@Override
    //public InputStream downloadAsStream(String fileName) throws IOException {
    //    File file = new File(fileName);
    //    file = download(removeFirstSlash(fileName), file);
    //    if (file != null) {
    //        try (InputStream inputStream = new FileInputStream(file)) {
    //            return inputStream;
    //        }
    //    }
    //    return null;
    //}

    /**
     * 删除文件
     * @param fileName
     */
    @Override
    public void delete(String fileName){
        getClient().deleteObject(getBucketName(), removeFirstSlash(fileName));
    }
    
    
}
