package com.spark.ims.common.listener;

/**
 * 功能描述:逻辑删除可监听
 *
 * Created by liyuan on 2018/4/27.
 */
public interface IDeleteListenable {

    /** 设置状态值 */
    void setStatus(String status);
}
