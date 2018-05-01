package com.spark.ims.common.listener;

/**
 * 描述:手动设置不更新修改人信息
 *
 *  Created by liyuan on 2018/4/27.
 */
public interface IModifyUpdatableListenable {

    public boolean getModifyUpdatable();

    public void setModifyUpdatable(boolean modifyUpdatable);
}
