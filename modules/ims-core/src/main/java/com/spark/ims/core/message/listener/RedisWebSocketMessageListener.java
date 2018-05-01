package com.spark.ims.core.message.listener;

import com.spark.ims.common.domain.RedisMessage;
import com.spark.ims.core.exception.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * redis消息监听器
 *
 * Created by liyuan on 2018/4/26.
 *
 */
@Component
public class RedisWebSocketMessageListener implements MessageListener {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	@Qualifier("redisMessageTemplate")
	private RedisTemplate redisMessageTemplate;

	public void onMessage(Message message, byte[] pattern) {
		RedisMessage redisMessage;
		try {
			redisMessage = (RedisMessage) redisMessageTemplate
							.getValueSerializer()
							.deserialize(message.getBody());
		} catch (SerializationException  e) {
			throw new SystemException("Could Not Deserialize Object!", e);
		}
		Object content = redisMessage.getObject();
		List<String> userIds = redisMessage.getUserIds();
		String channel = redisMessage.getChannel();
		boolean isBroadcast = redisMessage.isBroadcast();
		if (!channel.startsWith("ims.")) {
			throw new SystemException("Unknown Channel, Please StartWith ims. !");
		}
		if (isBroadcast) {
			simpMessagingTemplate.convertAndSend("/topic/" + channel, content);
		} else {
			for (String userId : userIds) {
				simpMessagingTemplate.convertAndSendToUser(userId, "/queue/"
						+ channel, content);
			}
		}
	}

}
