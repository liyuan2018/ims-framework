package com.spark.ims.service;


import com.spark.ims.common.domain.RedisMessage;

/**
 * redis pub/sub服务接口
 * 
 * Created by liyuan on 2018/4/26.
 *
 */
public interface IRedisPubSubService {

	/**
	 * 发布消息
	 * 
	 * @param redisMessage
	 */
	public void publish(RedisMessage redisMessage);

}
