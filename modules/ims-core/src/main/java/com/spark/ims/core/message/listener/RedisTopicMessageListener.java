package com.spark.ims.core.message.listener;

/**
 * Redis主题消息监听接口
 *
 *  Created by liyuan on 2018/4/26.
 */
public interface RedisTopicMessageListener<T> {

    /**
     * 接收消息触发回调函数
     * @param message       消息
     */
    void onMessage(T message);
}
