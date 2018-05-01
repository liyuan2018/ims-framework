package com.spark.ims.core.message;

import com.spark.ims.core.message.listener.RedisTopicMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Redis主题订阅/发布工具类
 *
 * 示例代码:
 * <code>
 *

    @Autowired
    private RedisTopic redisTopic;

    // 消息主题枚举
    public enum RedisTopicEnum {
        META_MODEL_CACHE_REFRESH(new PatternTopic("ims.de.metamodel.refresh"));

        private PatternTopic patternTopic;

        RedisTopicEnum(PatternTopic patternTopic) {
            this.patternTopic = patternTopic;
        }

        public PatternTopic getTopic() {
            return patternTopic;
        }
    }

    public void doMyBussinessLogic() {

        // 订阅消息主题
        redisTopic.subscribe(new RedisTopicMessageListener<Boolean>() {
            @Override
            public void onMessage(Boolean isMetaModelCacheRefreshed) {
                if (isMetaModelCacheRefreshed) {
                    // 清除缓存
                }
            }
        }, RedisTopicEnum.META_MODEL_CACHE_REFRESH.getTopic());

        // 向消息主题发布消息
        redisTopic.publish(RedisTopicEnum.META_MODEL_CACHE_REFRESH.getTopic(), true);
    }
 * </code>
 * Created by liyuan on 2018/4/26.
 */
@Component
public class RedisTopic {
    private RedisMessageListenerContainer container;
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisTopic(RedisMessageListenerContainer container, RedisTemplate<String, Object> redisTemplate) {
        this.container = container;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 订阅消息主题集合
     * @param redisTopicMessageListener     主题接收消息处理回调
     * @param topics                        消息主题集合
     */
    public void subscribe(RedisTopicMessageListener redisTopicMessageListener, Collection<Topic> topics) {
        container.addMessageListener(wrapMessageListener(redisTopicMessageListener), topics);
    }

    /**
     * 订阅消息主题
     * @param redisTopicMessageListener     主题接收消息处理回调
     * @param topic                         消息主题
     */
    public void subscribe(RedisTopicMessageListener redisTopicMessageListener, Topic topic) {
        container.addMessageListener(wrapMessageListener(redisTopicMessageListener), topic);
    }

    /**
     * 向消息主题发送消息
     * @param topic             消息主题
     * @param message           消息
     */
    public void publish(Topic topic, Object message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }

    /**
     * 获得消息监听适配
     * @param redisTopicMessageListener     Redis消息主题监听
     * @return                              消息监听适配
     */
    private MessageListener wrapMessageListener(RedisTopicMessageListener redisTopicMessageListener) {
        return new MessageListenerAdapter(createMessageListener(redisTopicMessageListener));
    }

    /**
     * 创建spring内部的消息监听
     * @param topicMessageListener      Redis消息主题监听
     * @param <T>                       消息类型
     * @return                          spring内部的消息监听
     */
    private <T> MessageListener createMessageListener(final RedisTopicMessageListener<T> topicMessageListener) {
        return new MessageListener() {
            public void onMessage(Message message, byte[] pattern) {
                T rawMessage = (T) redisTemplate.getValueSerializer().deserialize(message.getBody());
                topicMessageListener.onMessage(rawMessage);
            }
        };
    }
}
