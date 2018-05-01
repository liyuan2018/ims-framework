package com.spark.ims.web.config;

import com.spark.ims.common.util.Base64Utils;
import com.spark.ims.core.message.listener.RedisWebSocketMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.session.data.redis.ImsRedisOperationsSessionRepository;
import redis.clients.jedis.JedisPoolConfig;
import com.spark.ims.web.http.ImsCookieHttpSessionStrategy;
import com.spark.ims.web.http.ImsHttpSessionStrategy;
import com.spark.ims.web.http.TokenHttpSessionStrategy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * spring-session会话配置
 * <p>
 * Created by liyuan on 2018/4/26.
 */
@Configuration
@EnableRedisHttpSession
@EnableCaching
public class RedisSessionConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisSessionConfig.class);

    @Value("${server.session-timeout}")
    private int maxInactiveIntervalInSeconds;


    @Autowired
    protected RedisProperties properties;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        RedisProperties.Sentinel sentinelProperties = this.properties.getSentinel();
        if (sentinelProperties != null) {
            factory = new JedisConnectionFactory(getSentinelConfig());
        } else {
            factory.setHostName(this.properties.getHost());
            factory.setPort(this.properties.getPort());
            factory.setDatabase(this.properties.getDatabase());
        }
        factory.setUsePool(true);
        if (this.properties.getPassword() != null) {
            String password = properties.getPassword();
            try {
                password = Base64Utils.getFromBase64(properties.getPassword());
            } catch (Exception ex) {
                LOGGER.error("jedisConnectionFactory config password base64 decode failure!");
            } finally {
                factory.setPassword(password);
            }
        }
        if (this.properties.getTimeout() > 0) {
            factory.setTimeout(this.properties.getTimeout());
        }

        factory.setPoolConfig(jedisPoolConfig());
        return factory;
    }


    private final RedisSentinelConfiguration getSentinelConfig() {

        RedisProperties.Sentinel sentinelProperties = this.properties.getSentinel();
        if (sentinelProperties != null) {
            RedisSentinelConfiguration config = new RedisSentinelConfiguration();
            config.master(sentinelProperties.getMaster());
            config.setSentinels(createSentinels(sentinelProperties));
            return config;
        }
        return null;
    }

    private List<RedisNode> createSentinels(RedisProperties.Sentinel sentinel) {
        List<RedisNode> sentinels = new ArrayList<RedisNode>();
        String nodes = sentinel.getNodes();
        for (String node : StringUtils.commaDelimitedListToStringArray(nodes)) {
            try {
                String[] parts = StringUtils.split(node, ":");
                Assert.state(parts.length == 2, "Must be defined as 'host:port'");
                sentinels.add(new RedisNode(parts[0], Integer.valueOf(parts[1])));
            } catch (RuntimeException ex) {
                throw new IllegalStateException(
                        "Invalid redis sentinel " + "property '" + node + "'", ex);
            }
        }
        return sentinels;
    }


    private JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        RedisProperties.Pool props = this.properties.getPool();
        config.setMaxTotal(props.getMaxActive());
        config.setMaxIdle(props.getMaxIdle());
        config.setMinIdle(props.getMinIdle());
        config.setMaxWaitMillis(props.getMaxWait());
        config.setTestOnBorrow(false);
        config.setTestOnReturn(false);
        config.setTimeBetweenEvictionRunsMillis(-1);
        config.setLifo(true);
        return config;
    }


    @Primary
    @Bean
    public RedisOperationsSessionRepository sessionRepository(RedisTemplate sessionRedisTemplate) {
        ImsRedisOperationsSessionRepository sessionRepository = new ImsRedisOperationsSessionRepository(sessionRedisTemplate);
        sessionRepository.setDefaultMaxInactiveInterval(maxInactiveIntervalInSeconds);
        return sessionRepository;
    }

    @Bean
    public KeyGenerator cacheKeyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method,
                                   Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };

    }

    @Bean
    public CacheManager cacheManager(
            @SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
        redisCacheManager.setDefaultExpiration(24 * 60 * 60);
        return redisCacheManager;
    }

    /**
     * @return 返回类型
     * @Description: 防止redis入库序列化乱码的问题
     * @date 2018/4/12 10:54
     */
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());//key序列化
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(Object.class));  //value序列化
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public HttpSessionStrategy httpSessionStrategy() {
        ImsCookieHttpSessionStrategy cookieHttpSessionStrategy = new ImsCookieHttpSessionStrategy(maxInactiveIntervalInSeconds);
        HeaderHttpSessionStrategy headerHttpSessionStrategy = new HeaderHttpSessionStrategy();
        TokenHttpSessionStrategy ticketHttpSessionStrategy = new TokenHttpSessionStrategy();
        ImsHttpSessionStrategy imsHttpSessionStrategy = new ImsHttpSessionStrategy(
                cookieHttpSessionStrategy, headerHttpSessionStrategy, ticketHttpSessionStrategy);
        return imsHttpSessionStrategy;
    }

    /**
     * redis消息监听容器
     *
     * @param connectionFactory
     * @param redisWebSocketMessageListener
     * @return
     */
    @Bean
    RedisMessageListenerContainer container(
            RedisConnectionFactory connectionFactory,
            RedisWebSocketMessageListener redisWebSocketMessageListener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(
                redisMessageListenerAdapter(redisWebSocketMessageListener),
                new PatternTopic("ims.*"));
        return container;
    }

    /**
     * redis消息监听适配器
     *
     * @param redisWebSocketMessageListener
     * @return
     */
    @Bean
    public MessageListenerAdapter redisMessageListenerAdapter(
            RedisWebSocketMessageListener redisWebSocketMessageListener) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(
                redisWebSocketMessageListener);
        return adapter;
    }

}
