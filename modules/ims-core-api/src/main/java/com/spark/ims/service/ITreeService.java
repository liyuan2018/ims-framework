package com.spark.ims.service;



import com.spark.ims.common.domain.BaseModel;
import com.spark.ims.common.domain.TreeModel;

import java.util.Iterator;

/**
 * 后端树接口
 *
 * Created by liyuan on 2018/4/26.
 *
 * @param <T>
 */
public interface ITreeService<T extends BaseModel> {

	/**
	 * 初始化树
	 *
	 * @return
	 */
	public TreeModel<T> initTree();

	/**
	 * 添加节点
	 *
	 * @param parentNode
	 * @param childNode
	 */
	public void addNode(TreeModel<T> parentNode, TreeModel<T> childNode);

    TreeModel removeTreeNode(TreeModel root, Iterator children);
}
