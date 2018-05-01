package com.spark.ims.common.listener;

import com.spark.ims.common.mapper.UserIdMapper;
import com.spark.ims.common.util.BeanUtils;
import com.spark.ims.common.util.ParamUtils;
import com.spark.ims.common.util.SpringUtils;
import com.spark.ims.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EntityEventListener {

    private static final Logger logger = LoggerFactory.getLogger(EntityEventListener.class);

    private static final String SYSTEM_USER_ACCOUNT="app.core.system_user_account";

    private String getCurrentUserId() {

        String currentUserId = "";
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            Object user = attributes.getRequest().getSession().getAttribute("user");

            if (user != null) {
                Map map = BeanUtils.convertBean(user);
                if (!StringUtils.isEmpty(user)) {
                    currentUserId = (String) map.get("id");
                }
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            //获取配置的systemUserAccount查询获得id
            currentUserId = getSystemUserAccount();
        }

        return currentUserId;
    }

    private String getSystemUserAccount() {
        String currentUserId = "";
        try{

            Environment environment =  (Environment) SpringUtils.getBean("environment");
            if(environment!=null) {
                String systemUserAccount = environment.getProperty(SYSTEM_USER_ACCOUNT);
                if(systemUserAccount!=null) {
                    Map map = new HashMap();
                    map.put("account", systemUserAccount);
                    UserIdMapper userIdMapper = SpringUtils.getBean("userIdMapper");
                    currentUserId = userIdMapper.findId(map);
                }
            }
        }
        catch (Exception ex){
            logger.error(ex.getMessage());
        }
        return currentUserId;
    }

    /**
     * 创建实体前触发事件
     */
    @PrePersist
    public void prePersist(Object entity) {
        if (entity instanceof ICreateListenable) {
            if (ParamUtils.isNull(((ICreateListenable) entity).getCreatedTime()))
                ((ICreateListenable) entity).setCreatedTime(new Date(System.currentTimeMillis()));
            if (ParamUtils.isEmpty(((ICreateListenable) entity).getCreatorId()))
                ((ICreateListenable) entity).setCreatorId(getCurrentUserId());
        }
    }

    /**
     * 修改实体前触发事件
     */
    @PreUpdate
    public void preUpdate(Object entity) {
        if (entity instanceof IModifyListenable && entity instanceof IModifyUpdatableListenable) {
            if (((IModifyUpdatableListenable) entity).getModifyUpdatable()) {
                ((IModifyListenable) entity).setModifiedTime(new Date(System.currentTimeMillis()));
                ((IModifyListenable) entity).setModifierId(getCurrentUserId());
            }
        } else if (entity instanceof IModifyListenable) {
            ((IModifyListenable) entity).setModifiedTime(new Date(System.currentTimeMillis()));
            ((IModifyListenable) entity).setModifierId(getCurrentUserId());
        }

    }

}