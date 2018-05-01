package com.spark.ims.web.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Type;

/**
 * REST数据转换器
 * Created by liyuan on 2018/4/26.
 */
public class RESTDataHttpMessageConverter extends MappingJackson2HttpMessageConverter {

    private static final Logger logger = LoggerFactory.getLogger(RESTDataHttpMessageConverter.class.getName());

    private AbstractConverter converter;
    /** BPM平台客户端类型在http header中的Key */
    public static final String IMSBPM_CLIENT = "IMSBPM_CLIENT";

    @Override
    protected  void writeInternal(Object object, Type type, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {

        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

        if(RESTClientTypeEnum.EMBER.getName().equalsIgnoreCase(request.getHeader(IMSBPM_CLIENT))){ // 转成ember适用格式
            converter = EmberConverter.getInstance();
            logger.debug("切换成使用Ember转换器。");
        } else if (RESTClientTypeEnum.MOBILE.getName().equalsIgnoreCase(request.getHeader(IMSBPM_CLIENT))) {
            converter = EmberConverter.getInstance();
            logger.debug("切换成使用Mobile转换器。");
        } else { // 默认使用ember转换器
            converter = EmberConverter.getInstance();
            logger.debug("默认使用Ember转换器。");
        }

        super.writeInternal(converter.convert(object), type,outputMessage);
        logger.debug("使用转换器，且写入outputMessage内部成功完成。");
    }
}
