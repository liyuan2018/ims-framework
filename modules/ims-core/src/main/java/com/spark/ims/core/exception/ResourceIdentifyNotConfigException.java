package com.spark.ims.core.exception;


import com.spark.ims.common.constants.IMessage;

/**
 * Created by liyuan on 2018/4/26.
 */
public class ResourceIdentifyNotConfigException extends SystemException {

    public  ResourceIdentifyNotConfigException(String message){
        super(message);
    }

    public ResourceIdentifyNotConfigException(IMessage errorCode) {
        super(errorCode);
    }

    public ResourceIdentifyNotConfigException(IMessage errorCode, Object[] msgArgs) {
        super(errorCode,msgArgs);
    }

}
