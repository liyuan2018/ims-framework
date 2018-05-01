package com.spark.ims.storage.domain;

import org.springframework.data.redis.listener.PatternTopic;

/**
 * Created by liyuan on 2018/4/26.
 */
public enum CephTopicEnum {
    
    CEPH_CONFIG_REFRESH(new PatternTopic("app.com.spark.ims.storage.ceph.refresh"));
    
    /** spring封装的redis模式消息主题 */
    private PatternTopic patternTopic;
    
    CephTopicEnum(PatternTopic patternTopic) {
        this.patternTopic = patternTopic;
    }
    
    /** 获得消息主题 */
    public PatternTopic getTopic() {
        return patternTopic;
    }
}
