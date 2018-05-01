package com.spark.ims.core.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by liyuan on 2018/4/26.
 */
public class UserInfo implements Serializable{


    private static final long serialVersionUID = -8531337909338000593L;

    private String  id;

    private String orgId;

    private String deptId;

    private String  name;

    private String  account;

    private String email;

    private String mobile;

    private String sex;

    private String address;

    private String homePhone;
    //头像
    private String avatar;

    private String description;

    private Date createTime;

    private String appId;

    private String sessionId;

    // 所属组织机构名称
    private String orgName;
    //所属组织部门名称
    private String deptName;

    private List sysPositions;

    private List sysOrgs;

    private List sysDepts;

    private List sysRoles;

    private List sysGroups;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getSysPositions() {
        return sysPositions;
    }

    public void setSysPositions(List sysPositions) {
        this.sysPositions = sysPositions;
    }

    public List getSysOrgs() {
        return sysOrgs;
    }

    public void setSysOrgs(List sysOrgs) {
        this.sysOrgs = sysOrgs;
    }

    public List getSysDepts() {
        return sysDepts;
    }

    public void setSysDepts(List sysDepts) {
        this.sysDepts = sysDepts;
    }

    public List getSysRoles() {
        return sysRoles;
    }

    public void setSysRoles(List sysRoles) {
        this.sysRoles = sysRoles;
    }

    public List getSysGroups() {
        return sysGroups;
    }

    public void setSysGroups(List sysGroups) {
        this.sysGroups = sysGroups;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
