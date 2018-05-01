package com.spark.ims.web.session;

import com.spark.ims.auth.constants.ITokenConstants;

/**
 * Session管理类
 *
 * Created by liyuan on 2018/4/26.
 */
public class ImsSessionManager {
    /** 平台App Id */
    private static final ThreadLocal<String> APP_ID = ThreadLocal.withInitial(() -> ITokenConstants.DEFAULT_APP_ID);

    public String getAppId() {
        return APP_ID.get();
    }

    public void setAppId(String appId) {
        APP_ID.set(appId);
    }

    /** 获得单例 */
    public static ImsSessionManager getInstance() {
        return ImsSessionManagerHolder.INSTANCE;
    }

    // for singleton
    private ImsSessionManager() {}
    private static class ImsSessionManagerHolder {
        private static final ImsSessionManager INSTANCE = new ImsSessionManager();
    }
}