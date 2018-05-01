package com.spark.ims.core.enums;

/**
 * websocket类型枚举
 *
 * Created by liyuan on 2018/4/26.
 */
public enum WebSocketEnum {

    SESSION_USER_ID("userId"),

    WEBSOCKET_KEY("USER_ID");

    /** 状态值 */
    private String value;

    WebSocketEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public WebSocketEnum getByValue(String value) {
        for (WebSocketEnum webSocketEnum : WebSocketEnum.values()) {
            if (webSocketEnum.getValue() == value) {
                return webSocketEnum;
            }
        }

        return WebSocketEnum.SESSION_USER_ID; // 默认返回启用
    }
}
