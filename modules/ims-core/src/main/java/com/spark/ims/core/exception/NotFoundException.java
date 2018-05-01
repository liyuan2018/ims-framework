package com.spark.ims.core.exception;

/**
 * @Description 对象没查询到返回的一次
 * Created by liyuan on 2018/4/26.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(){
        super();
    }

    public NotFoundException(String id){
        super("could not found the model with id: "+ id);
    }
}
