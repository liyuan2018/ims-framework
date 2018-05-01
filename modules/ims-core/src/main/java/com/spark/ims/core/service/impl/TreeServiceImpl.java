package com.spark.ims.core.service.impl;

import com.spark.ims.common.domain.BaseModel;
import com.spark.ims.common.domain.TreeModel;
import org.springframework.stereotype.Service;
import com.spark.ims.service.ITreeService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * ITreeService实现类
 *
 * @param <T>
 * Created by liyuan on 2018/4/26.
 */
@Service("treeService")
public class TreeServiceImpl<T extends BaseModel> implements ITreeService<T> {

    public TreeModel<T> initTree() {
        TreeModel<T> root = new TreeModel<T>();
        root.setId("root");
        root.setData(null);
        root.setParentId(null);
        root.setLevel(null);
        root.setIsParent(true);
        root.setIcon(null);
        root.setChildren(null);
        return root;
    }

    public void addNode(TreeModel<T> parentNode, TreeModel<T> childNode) {
        List<TreeModel<T>> childrenList = parentNode.getChildren();
        if (childrenList == null) {
            childrenList = new ArrayList<TreeModel<T>>();
        }
        childrenList.add(childNode);
        parentNode.setChildren(childrenList);
    }

    private boolean isCanRemove(TreeModel treeModel) {
        boolean flag;
        if (treeModel.isChecked()) {
            flag = true;
        } else {
            return false;
        }
        for (TreeModel children : (List<TreeModel>) treeModel.getChildren()) {
            if (children.isChecked()) {
                flag = isCanRemove(children);
                if (!flag) {
                    break;
                }
            } else {
                flag = false;
                break;
            }
        }
        return flag;
    }

    public TreeModel removeTreeNode(TreeModel root, Iterator children) {
        if (isCanRemove(root)) {
            if (children != null) {
                children.remove();
            }
            return null;
        } else {
            for (Iterator it = root.getChildren().iterator(); it.hasNext(); ) {
                TreeModel itChildren = (TreeModel) it.next();
                removeTreeNode(itChildren, it);
            }
        }
        return root;
    }

}
