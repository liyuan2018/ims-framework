package org.springframework.session.data.redis;

import com.spark.ims.auth.constants.ITokenConstants;
import com.spark.ims.web.session.ImsSessionManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.session.ExpiringSession;

/**
 * RedisSessionDAO类
 *
 * Created by liyuan on 2018/4/26.
 */
public class ImsRedisOperationsSessionRepository extends RedisOperationsSessionRepository {
    public ImsRedisOperationsSessionRepository(RedisConnectionFactory redisConnectionFactory) {
        super(redisConnectionFactory);
    }

    public ImsRedisOperationsSessionRepository(RedisOperations<Object, Object> sessionRedisOperations) {
        super(sessionRedisOperations);
    }

    @Override
    public RedisOperationsSessionRepository.RedisSession createSession() {
        RedisSession session = super.createSession();
        // 移动端app延长session过期时间
        if (ImsSessionManager.getInstance().getAppId().equalsIgnoreCase(ITokenConstants.MOBILE_DEFAULT_APP_ID)) {
            session.setMaxInactiveIntervalInSeconds(ITokenConstants.DEFAULT_TOKEN_EXPIRE_MOBILE);
        }
        return session;
    }
}