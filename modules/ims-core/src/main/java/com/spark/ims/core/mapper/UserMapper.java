package com.spark.ims.core.mapper;

import com.spark.ims.core.domain.UserInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 描述:
 *
 * Created by liyuan on 2018/4/26.
 */
public interface UserMapper {

    UserInfo find(@Param("account") String account);
}
