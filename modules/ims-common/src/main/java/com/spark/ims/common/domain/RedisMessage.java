package com.spark.ims.common.domain;

import java.io.Serializable;
import java.util.List;

public class RedisMessage implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8466556681876191387L;

    private Object object;
    private List<String> userIds;
    private String channel;
    private boolean broadcast;

    public RedisMessage() {

    }

    public RedisMessage(Object object, List<String> userIds, String channel,
                        boolean broadcast) {
        this.object = object;
        this.userIds = userIds;
        this.channel = channel;
        this.broadcast = broadcast;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public boolean isBroadcast() {
        return broadcast;
    }

    public void setBroadcast(boolean broadcast) {
        this.broadcast = broadcast;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

}
