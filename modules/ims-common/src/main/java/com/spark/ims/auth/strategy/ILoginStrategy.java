package com.spark.ims.auth.strategy;

/**
 * 描述:登录策略接口
 *
 * Created by liyuan on 2018/4/26.
 */
public interface ILoginStrategy {

    /**
     * 登录认证接口
     * @param account  账号
     * @param password 密码
     * @param loginType 登录类型
     * @param appId  应用Id:工具/com.spark.ims.web
     * @return
     */
    boolean auth(String account, String password, String loginType, String appId) throws Exception;
}
