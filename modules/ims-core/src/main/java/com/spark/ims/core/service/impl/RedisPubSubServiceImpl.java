package com.spark.ims.core.service.impl;

import com.spark.ims.common.domain.RedisMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.spark.ims.service.IRedisPubSubService;

/**
 * redis pub/sub服务实现类
 * 
 * @Created by liyuan on 2018/4/26.
 *
 */
@Service("redisPubSubService")
public class RedisPubSubServiceImpl implements IRedisPubSubService {

	@Autowired
	private RedisTemplate<String, Object> messageRedisTemplate;

	public void publish(RedisMessage redisMessage) {
		messageRedisTemplate.convertAndSend(redisMessage.getChannel(), redisMessage);
	}

}
