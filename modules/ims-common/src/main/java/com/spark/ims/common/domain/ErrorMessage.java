package com.spark.ims.common.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by liyuan on 2018/4/27.
 */
public class ErrorMessage extends LinkedHashMap<String, Object> implements Serializable {

    private static final long serialVersionUID = 811899486654614381L;

    private String code;
    private String message;
    private String moreInfo;
    private Object data;
    private  void setErrorMap(){
        HashMap messageMap = new HashMap<String,String>();
        messageMap.put("code", code);
        messageMap.put("message", message);
        messageMap.put("moreInfo", moreInfo);
        messageMap.put("data", data);
        put("error", messageMap);
    }

    public ErrorMessage(String code, String message, String moreInfo) {
        this.setCode(code);
        this.setMessage(message);
        this.setMoreInfo(moreInfo);
        //map赋值
        setErrorMap();
    }

    public ErrorMessage(String code, String message) {
        this.setCode(code);
        this.setMessage(message);
        //map赋值
        setErrorMap();
    }

    public ErrorMessage(String code, String message,Object data) {
        this.setCode(code);
        this.setMessage(message);
        this.setData(data);
        //map赋值
        setErrorMap();
    }

    public ErrorMessage(String message) {
        this.setMessage(message);
        //map赋值
        setErrorMap();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    @Override
    public String toString() {
        return "error{ code='" + this.code + '\'' +
                ", message='" + this.message + '\'' +
                ", moreInfo='" + moreInfo + '\'' +
                '}';
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
