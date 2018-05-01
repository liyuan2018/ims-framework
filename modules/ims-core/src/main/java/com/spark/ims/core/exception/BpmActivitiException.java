package com.spark.ims.core.exception;


import com.spark.ims.common.constants.IMessage;

/**
 * Created by liyuan on 2018/4/26.
 */
public class BpmActivitiException extends BusinessException {

    public BpmActivitiException(Throwable cause) {
        super(cause);
    }

    public BpmActivitiException(String message) {
        super(message);
    }

    public BpmActivitiException(String message, Throwable cause) {
        super(message, cause);
    }

    public BpmActivitiException(IMessage errorCode, Throwable cause) {
        super(errorCode,cause);
    }
}
