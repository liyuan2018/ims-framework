package com.spark.ims.storage.controller;

import com.spark.ims.common.domain.ResultData;
import com.spark.ims.core.annotation.Rest;
import com.spark.ims.core.message.RedisTopic;
import com.spark.ims.storage.component.CephStorageComponent;
import com.spark.ims.storage.domain.CephTopicEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by liyuan on 2018/4/26.
 */
@Rest
@RequestMapping("/ceph/config")
public class CephConfigController {
    
    @Autowired
    private RedisTopic redisTopic;
    @Autowired
    private CephStorageComponent cephStorageComponent;
    
    @RequestMapping(value = "/refresh",method = RequestMethod.GET)
    @ResponseBody
    public ResultData refreshCephConfig(){
        redisTopic.publish(
            CephTopicEnum.CEPH_CONFIG_REFRESH.getTopic(),"ceph config refresh!"
        );
        return new ResultData();
    }
    
    @RequestMapping(value = "/test/{bucketName}",method = RequestMethod.GET)
    @ResponseBody
    public ResultData test(@PathVariable(value = "bucketName") String bucketName){
        return cephStorageComponent.listObjects(bucketName);
    }

}
