package com.spark.ims.common.listener;

import java.util.Date;

/**
 * 修改者和修改时间可监听
 * （通过监听器自动设置值）
 *  Created by liyuan on 2018/4/27.
 */
public interface IModifyListenable {
    /** 设置修改者 */
    void setModifierId(String userId);

    /** 设置修改时间 */
    void setModifiedTime(Date now);
    
    /** 获取修改者*/
    public String getModifierId();
    
    /** 获取修改时间*/
    public Date getModifiedTime();    
}
