package com.spark.ims.storage.listener;

import com.spark.ims.core.message.RedisTopic;
import com.spark.ims.core.message.listener.RedisTopicMessageListener;
import com.spark.ims.storage.component.CephStorageComponent;
import com.spark.ims.storage.domain.CephTopicEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Created by liyuan on 2018/4/26.
 */

@Component
public class CephConfigRefreshListener {
    
    @Autowired
    private RedisTopic redisTopic;
    @Autowired
    private CephStorageComponent cephStorageComponent;
    
    @EventListener
    public void handleContextRefresh(ApplicationReadyEvent event) {
        subscribeCephConfigRefresh();
    }
    
    private void subscribeCephConfigRefresh() {
        redisTopic.subscribe(new RedisTopicMessageListener<String>() {
            @Override
            public void onMessage(String msg){
                cephStorageComponent.refreshClientConfig();
            }
        }, CephTopicEnum.CEPH_CONFIG_REFRESH.getTopic());
    }
}
