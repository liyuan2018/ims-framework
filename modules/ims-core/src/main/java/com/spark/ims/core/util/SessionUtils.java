package com.spark.ims.core.util;

import com.spark.ims.common.constants.ErrorCode;
import com.spark.ims.common.util.BeanUtils;
import com.spark.ims.common.util.StringUtils;
import com.spark.ims.core.domain.UserInfo;
import com.spark.ims.core.exception.SessionTimeoutException;
import com.spark.ims.core.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class SessionUtils {

    private static Logger logger = LoggerFactory.getLogger(SessionUtils.class);


    private static final String SYSTEM_USER_ACCOUNT="app.core.system_user_account";

	public static UserInfo getCurrentUser(){

        RequestAttributes attributes =RequestContextHolder.getRequestAttributes();
        if(attributes==null){
            Environment environment= (Environment)ApplicationContextUtils.getApplicationContext().getBean("environment");
            if(environment!=null&& environment.getProperty(SYSTEM_USER_ACCOUNT)!=null){
                UserMapper userMapper = (UserMapper)ApplicationContextUtils.getApplicationContext().getBean("userMapper");
                //后端调用
                UserInfo  userInfo =  userMapper.find(environment.getProperty(SYSTEM_USER_ACCOUNT));
                return userInfo;
            }
            else
                return null;
        }
        else{
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)attributes;
            HttpServletRequest httpServletRequest = ((HttpServletRequest) servletRequestAttributes.getRequest());
            Object user = httpServletRequest.getSession().getAttribute("user");
            if(!StringUtils.isEmpty(user)){
                return (UserInfo)user;
            }
            else
                return null;
        }
	}

    public static boolean hasRoleId(List<String> roleIds){
        try {
            UserInfo userInfo = getCurrentUser();
            if(userInfo!=null){
                List list =  userInfo.getSysRoles();
                if(list!=null){
                    for(Object obj :list){
                        Map map = BeanUtils.convertBean(obj);
                        String roleId = (String)map.get("id");
                        if(roleIds.contains(roleId)){
                            return  true;
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
        }
        return  false;
    }

    public static boolean hasRoleCode(List<String> roleCodes){
        try {
            UserInfo userInfo = getCurrentUser();
            if(userInfo!=null){
                List list =  userInfo.getSysRoles();
                if(list!=null){
                    for(Object obj :list){
                        Map map = BeanUtils.convertBean(obj);
                        String code = (String)map.get("code");
                        if(roleCodes.contains(code)){
                            return  true;
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
        }
        return  false;
    }

	public static String getCurrentUserId() {

        UserInfo userInfo = getCurrentUser();
        if(userInfo!=null){
            return userInfo.getId();
		}
        else{
            throw new SessionTimeoutException(ErrorCode.Common.sessionTimeout);
        }
	}
}
