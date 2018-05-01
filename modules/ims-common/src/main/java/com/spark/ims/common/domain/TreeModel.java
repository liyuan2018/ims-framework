package com.spark.ims.common.domain;

import com.spark.ims.common.util.ClassUtils;
import com.spark.ims.common.util.ParamUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TreeModel<T extends BaseModel> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7392469928339829955L;

    private String id;// 节点ID
    private String name;// 节点名称
    private T data;// 节点内容
    private String parentId;// 父节点ID
    private String level;// 层级
    private boolean isParent;// 是否叶子
    private String icon;// 图标
    private boolean nocheck;// 节点是否隐藏 checkbox / radio
    private boolean chkDisabled;// 节点的 checkbox / radio 是否禁用
    private boolean checked;// 节点的 checkBox / radio 的 勾选状态
    private String iconSkin;// "org"||"dept"
    private List<TreeModel<T>> children = new ArrayList<TreeModel<T>>(); // 孩子
    private String type;

    public TreeModel() {
        super();
    }

    public TreeModel(String id, String name, T data, String parentId,
                     String iconSkin) {
        super();
        this.id = id;
        this.name = name;
        this.data = data;
        this.parentId = parentId;
        this.iconSkin = iconSkin;
        if(ParamUtils.isNotNull(data)) {
            this.type = ClassUtils.getEntityNameWithoutDto(data.getClass());
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(boolean isParent) {
        this.isParent = isParent;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<TreeModel<T>> getChildren() {
        return children;
    }

    public void setChildren(List<TreeModel<T>> children) {
        this.children = children;
    }

    public void addChild(TreeModel<T> child) {
        if (children == null) {
            children = new ArrayList<TreeModel<T>>();
        }
        children.add(child);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconSkin() {
        return iconSkin;
    }

    public void setIconSkin(String iconSkin) {
        this.iconSkin = iconSkin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isNocheck() {
        return nocheck;
    }

    public void setNocheck(boolean nocheck) {
        this.nocheck = nocheck;
    }

    public boolean isChkDisabled() {
        return chkDisabled;
    }

    public void setChkDisabled(boolean chkDisabled) {
        this.chkDisabled = chkDisabled;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
