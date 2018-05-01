package com.spark.ims.core.component;

import com.spark.ims.common.constants.IParameter;
import com.spark.ims.core.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Base64;

/**
 * 描述:系统参数工具类
 *
 * Created by liyuan on 2018/4/26.
 */
@Component
public class ConfigurationParameter {

    private ConfigurableConversionService conversionService = new DefaultConversionService();

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationParameter.class);

    @Autowired
    private Environment environment;

    public  String getProperty(IParameter parameter) {
        return this.getProperty(parameter,true);
    }

    /**
     *
     * @param parameter
     * @param isRequired
     * @return
     */
    public  String getProperty(IParameter parameter,Boolean isRequired) {
        String value = environment.getProperty(parameter.getValue());
        if(isRequired && StringUtils.isEmpty(value)){
            throw new BusinessException("configuration parameter cannot found,code:"+parameter.getValue());
        }
        logger.debug("configuration parameter code:"+parameter.getValue());
        if(parameter.getEncryptEnable()){
            if(parameter.getEncryptEnable() && value!=null){
                //TODO 需要进行解密,后续改成平台统一的加密解密策略
                value = new String(Base64.getDecoder().decode(value.getBytes()));
            }
        }

        return value;
    }

    public <T> T getProperty(IParameter parameter,Class<T> var2) {
        String value = this.getProperty(parameter);
        return  this.conversionService.convert(value,var2);
    }

}
