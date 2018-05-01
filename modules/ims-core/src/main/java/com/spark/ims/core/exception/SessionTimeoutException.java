package com.spark.ims.core.exception;


import com.spark.ims.common.constants.IMessage;

/**
 * Created by liyuan on 2018/4/26.
 */
public class SessionTimeoutException extends BusinessException {

    /** serialVersionUID */
    private static final long serialVersionUID = 2332608236621015980L;

    public SessionTimeoutException(String message) {
        super(message);
    }

    public SessionTimeoutException(IMessage errorCode) {
        super(errorCode);
    }

    public SessionTimeoutException(IMessage errorCode, Object[] msgArgs) {
         super(errorCode,msgArgs);
    }


}
