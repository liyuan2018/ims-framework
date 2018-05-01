package com.spark.ims.storage.constants;


import com.spark.ims.common.constants.IParameter;

public interface ParameterConstants {

    enum Storage implements IParameter {
        storageType("app.com.spark.ims.storage.type","文件存储类型");
    
        private String code;
    
        private String name;
    
        private boolean encryptEnable = false;
    
        Storage(String code,String name){
            this.code = code;
            this.name = name;
        }
    
        Storage(String code,String name,boolean encryptEnable){
            this.code = code;
            this.name = name;
            this.encryptEnable = encryptEnable;
        }
    
        public Boolean getEncryptEnable(){
            return encryptEnable;
        }
    
        public String getValue(){
            return code;
        }
    
        public String getName(){
            return name;
        }
    
        public String toString(){
            return code;
        }
    }
    
    enum Ceph implements IParameter {
        bucketName("app.storage.ceph.bucket_name","存储空间名称"),
        region("app.com.spark.ims.storage.ceph.region","存储区域"),
        accessKey("app.storage.ceph.access_key","分布式存储的密钥ak"),
        secretKey("app.storage.ceph.secret_key","分布式存储的密钥sk"),
        host("app.storage.ceph.host","分布式存储访问地址");
        //httpProtocol("app.ceph.http.protocol","分布式存储访问协议");

        private String code;

        private boolean encryptEnable = false;

        private String name;

        Ceph(String code,String name){
            this.code = code;
            this.name = name;
        }

        Ceph(String code,String name,boolean encryptEnable){
            this.code = code;
            this.name = name;
            this.encryptEnable = encryptEnable;
        }

        public Boolean getEncryptEnable(){
            return encryptEnable;
        }

        public String getValue(){
            return code;
        }

        public String getName(){
            return name;
        }

        public String toString(){
            return code;
        }
    }
}
