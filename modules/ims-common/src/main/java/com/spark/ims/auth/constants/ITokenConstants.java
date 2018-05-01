package com.spark.ims.auth.constants;

/**
 * token 常量
 * Created by liyuan on 2018/4/26.
 */
public interface ITokenConstants {

    String APP_ID_KEY = "app_id";

    /** 默认APP_ID -> com.spark.ims.web */
    String DEFAULT_APP_ID = "APP0000000001";

    /** 客户端默认APP_ID */
    String CLIENT_DEFAULT_APP_ID="APP0000000002";

    /** 移动端默认APP_ID */
    String MOBILE_DEFAULT_APP_ID="APP0000000003";

    /** 移动端默认超时时间 30天 */
    int DEFAULT_TOKEN_EXPIRE_MOBILE = 30 * 24 * 60 * 60;

}
