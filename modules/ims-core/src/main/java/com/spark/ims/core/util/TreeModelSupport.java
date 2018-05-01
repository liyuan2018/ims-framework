package com.spark.ims.core.util;


import com.spark.ims.common.domain.BaseModel;
import com.spark.ims.common.domain.TreeModel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liyuan on 2018/4/26.
 */
public class TreeModelSupport {

    private volatile static TreeModelSupport support;
    private static final String GETPARANTID_METHODNAME = "getParentId";
    private List<Object> emptyTag = new ArrayList<Object>();

    private TreeModelSupport() {
    }

    {
        emptyTag.add("");
        emptyTag.add("0");
        emptyTag.add(null);
    }

    public static TreeModelSupport getInstance() {
        if (support == null) {
            synchronized (TreeModelSupport.class) {
                if (support == null) {
                    support = new TreeModelSupport();
                }
            }
        }
        return support;
    }

    /**
     * sort the models
     *
     * @param models
     * @param <T>
     * @return
     */
    public <T extends BaseModel> TreeModel<T> sortTreeModel(List<T> models) {
        if (models == null || models.size() == 0) {
            throw new RuntimeException("input models is empty , please check");
        }

        Method getParentIdMethod = getParentIdMethod(models.get(0));
        TreeModel<T> rootNode = null, tempNode = null;
        Map<String, TreeModel> tempNodeMap = new HashMap<String, TreeModel>();

        for (T model : models) {
            tempNode = convertModel(model, getParentIdMethod);
            if (isRoot(tempNode.getParentId())) {//root node
                if (tempNodeMap.containsKey(tempNode.getId())) {
                    tempNode.getChildren().addAll((tempNodeMap.get(tempNode.getId()).getChildren()));
                }
                rootNode = tempNode;
                tempNodeMap.put(tempNode.getId(), rootNode);
            } else {//other node
                if (tempNodeMap.containsKey(tempNode.getId())) {//contain the key id add children
                    tempNode.getChildren().addAll((tempNodeMap.get(tempNode.getId()).getChildren()));
                }
                String parentId = tempNode.getParentId();
                if (tempNodeMap.containsKey(parentId)) {//contain the key id
                    tempNodeMap.get(tempNode.getParentId()).addChild(tempNode);
                    tempNodeMap.get(tempNode.getParentId()).setIsParent(true);
                } else {//not contain the key id
                    TreeModel<T> parentNode = new TreeModel<T>();
                    parentNode.setId(parentId);
                    parentNode.addChild(tempNode);
                    parentNode.setIsParent(true);
//                    parentNode.setLeaf(false);
                    tempNodeMap.put(parentId, parentNode);
                }
                tempNodeMap.put(tempNode.getId(), tempNode);
            }
        }

        return rootNode;
    }

    private <T extends BaseModel> TreeModel convertModel(T model, Method getParentIdMethod) {
        if (model == null) {
            throw new RuntimeException("input model is null , please check");
        }

        TreeModel<T> treeModel = new TreeModel<T>();
        treeModel.setId(model.getId());
        treeModel.setData(model);
        treeModel.setIsParent(false);
//        treeModel.setLeaf(true);
        try {
            treeModel.setParentId((String) getParentIdMethod.invoke(model));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return treeModel;
    }

    private <T extends BaseModel> Method getParentIdMethod(T model) {
        if (model == null) {
            throw new RuntimeException("getParentIdMethod model is null , please check");
        }

        try {
            return model.getClass().getMethod(GETPARANTID_METHODNAME);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(model.getClass().getSimpleName() + " not contain getParentId method , please check");
        }
    }

    private boolean isRoot(String pId) {
        return emptyTag.contains(pId);
    }

}
