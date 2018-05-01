package com.spark.ims.core.exception;


import com.spark.ims.common.constants.IMessage;

/**
 * Created by liyuan on 2018/4/26.
 */
public class ResourceNotFoundException extends SystemException {

    private static final long serialVersionUID = 5651320805529046976L;

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(IMessage errorCode) {
        super(errorCode);
    }

    public ResourceNotFoundException(IMessage errorCode, Object[] msgArgs) {
        super(errorCode,msgArgs);
    }

}
