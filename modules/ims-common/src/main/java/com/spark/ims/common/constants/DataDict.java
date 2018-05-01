package com.spark.ims.common.constants;

public interface DataDict {

    /**
     * 通用基础-性别
     */
    enum SysUserSex{

        MALE("1", "男"),
        FEMALE("2", "女");

        private final String code;

        private final String name;

        private SysUserSex(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getName(){
            return  this.name;
        }
        @Override
        public String toString() {
            return code;
        }
    };
    /**
     * 通用基础-逻辑运算符
     */
    enum OperatorLogic{

        AND("&&", "与"),
        OR("||", "或"),
        NOT("!", "非"),
        TRUE("1", "真"),
        FALSE("0", "假");

        private final String code;

        private final String name;

        private OperatorLogic(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getName(){
            return  this.name;
        }
        @Override
        public String toString() {
            return code;
        }
    };
    /**
     * 通用基础-关系运算符-字符串
     */
    enum OperatorRelationCommon{

        GREAT_THAN(">", "大于"),
        LESS_THAN("<", "小于"),
        EQUALS("=", "等于"),
        GREAT_EQUALS(">=", "大于等于"),
        LESS_EQUALS("<=", "小于等于"),
        NOT_EQUALS("!=", "不等于"),
        WHOLE_EQUALS("equals()", "完全等于"),
        EQUALS_IGNORECASE("equalsIgnoreCase()", "完全等于(忽略大小写)");

        private final String code;

        private final String name;

        private OperatorRelationCommon(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getName(){
            return  this.name;
        }
        @Override
        public String toString() {
            return code;
        }
    };
    /**
     * 通用基础-关系运算符-集合
     */
    enum OperatorRelationCollection{

        CONTAINS("contains()", "包含"),
        NOT_CONTAINS("notContains()", "不包含");

        private final String code;

        private final String name;

        private OperatorRelationCollection(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getName(){
            return  this.name;
        }
        @Override
        public String toString() {
            return code;
        }
    };
    /**
     * 通用基础-数据状态-公共
     */
    enum DataStatus{

        DELETE("0", "已删除"),
        ENABLE("1", "启用"),
        DISABLE("2", "禁用");

        private final String code;

        private final String name;

        private DataStatus(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getName(){
            return  this.name;
        }
        @Override
        public String toString() {
            return code;
        }
    };
    /**
     * 通用基础-数据状态-用户
     */
    enum SysUserStatus{

        DELETE("0", "已删除"),
        ENABLE("1", "启用"),
        DISABLE("2", "禁用"),
        LOCK("3", "锁定");

        private final String code;

        private final String name;

        private SysUserStatus(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getName(){
            return  this.name;
        }
        @Override
        public String toString() {
            return code;
        }
    };
    /**
     * 通用基础-机构层级
     */
    enum SysOrgLevel{

        HEAD("1", "总行"),
        BRANCH("2", "分行"),
        SUB_BRANCH("3", "支行");

        private final String code;

        private final String name;

        private SysOrgLevel(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getName(){
            return  this.name;
        }
        @Override
        public String toString() {
            return code;
        }
    };
    /**
     * 通用基础-树-根节点
     */
    enum TreeRootParentid{

        ROOT("0", "根节点");

        private final String code;

        private final String name;

        private TreeRootParentid(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getName(){
            return  this.name;
        }
        @Override
        public String toString() {
            return code;
        }
    };
    /**
     * 通用基础-用户类型
     */
    enum UserType{

        USER("SysUser", "用户"),
        POSITION("SysPosition", "岗位"),
        ROLE("SysRole", "角色"),
        ORG("SysOrg", "机构"),
        DEPT("SysDept", "部门"),
        GROUP("SysGroup", "群组");

        private final String code;

        private final String name;

        private UserType(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getName(){
            return  this.name;
        }
        @Override
        public String toString() {
            return code;
        }
    };

    /**
     * 通用基础-分隔符
     */
    enum Separator {

        COMMA(",", "逗号"),
        SHUN_SLASH("/", "顺斜杠"),
        POINT(".", "点"),
        SEMICOLON(";", "分号");

        private final String code;

        private final String name;

        private Separator(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        @Override
        public String toString() {
            return code;
        }
    }

    ;

    /**
     * 通用基础-文件后缀
     */
    enum Suffix{

        XML("xml", "XML文件后缀"),
        BPMN("bpmn20.xml", "流程定义文件后缀"),
        PNG("png", "PNG图形文件后缀"),
        JPG("jpg", "JPG图形文件后缀"),
        ZIP("zip", "压缩文件后缀"),
        IMS_BPMN_XML("ims.bpm.xml", "扩展流程定义文件后缀"),
        IMS_BPMN_ZIP("ims.bpm.zip", "扩展流程定义压缩文件后缀");

        private final String code;

        private final String name;

        private Suffix(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getName(){
            return  this.name;
        }
        @Override
        public String toString() {
            return code;
        }
    };
    /**
     * 通用基础-消息-发送方式
     */
    enum NoticeMode{

        LETTER("1", "站内信"),
        MAIL("2", "邮件"),
        MESSAGE("3", "手机短信");

        private final String code;

        private final String name;

        private NoticeMode(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getName(){
            return  this.name;
        }
        @Override
        public String toString() {
            return code;
        }
    };
    /**
     * 通用基础-日历类型
     */
    enum CalendarType{

        WORKDAY("workday", "工作日"),
        HOLIDAY("holiday", "节假日"),
        WEEKEND_DAY("weekend_day", "双休日");

        private final String code;

        private final String name;

        private CalendarType(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getName(){
            return  this.name;
        }
        @Override
        public String toString() {
            return code;
        }
    };
    /**
     * 通用基础-消息阅读状态
     */
    enum MessageReadStatus{

        UN_READ("0", "未读"),
        READ("1", "已读"),
        ALL("2", "全部");

        private final String code;

        private final String name;

        private MessageReadStatus(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getName(){
            return  this.name;
        }
        @Override
        public String toString() {
            return code;
        }
    };
    /**
     * 通用基础-消息状态
     */
    enum MessageStatus{

        DELETED("0", "已失效"),
        NO_PUBLISH("1", "未发布"),
        PUBLISHED("2", "已发布");

        private final String code;

        private final String name;

        private MessageStatus(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getName(){
            return  this.name;
        }
        @Override
        public String toString() {
            return code;
        }
    };
    /**
     * 通用基础-消息类型
     */
    enum MessageType {
        ANNOUNCE_MESSAGE("announce_message", "公告"),
        TASK_MESSAGE("task_message","任务"),
        USER_MESSAGE("user_message","私信"),
        STAR_MESSAGE("star_message", "星标"),
        SYSTEM_MESSAGE("system_message","系统");
        private final String code;
        private final String name;
        private MessageType(String code, String name) {
            this.code = code;
            this.name = name;
        }
        public String getName(){
            return  this.name;
        }
        @Override
        public String toString() {
            return code;
        }
    };

    enum TaskMessageType {
        TASK_MESSAGE("task_message", "任务消息"),
        REJECT_MESSAGE("reject_message", "驳回消息"),
        TRANSFER_MESSAGE("transfer_message", "转办消息"),
        AGENT_MESSAGE("agent_message", "代理消息"),
        BUSINESS_MESSAGE("business_message", "业务消息");
        private final String code;
        private final String name;
        private TaskMessageType(String code, String name) {
            this.code = code;
            this.name = name;
        }
        public String getName(){
            return  this.name;
        }
        @Override
        public String toString() {
            return code;
        }
    };
    /**
     * 通用基础-消息优先级
     */
    enum NoticePriority{

        URGENCY("1", "紧急"),
        HIGH("2", "高"),
        COMMON("3", "普通");

        private final String code;

        private final String name;

        private NoticePriority(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getName(){
            return  this.name;
        }
        @Override
        public String toString() {
            return code;
        }
    };
    /**
     * 通用基础-文档关联类型
     */
    enum DocumentType{

        MESSAGE("MESSAGE", "消息"),
        EMAIL("EMAIL", "邮件"),
        ANNOUNCE("ANNOUNCE", "公告");

        private final String code;

        private final String name;

        private DocumentType(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getName(){
            return  this.name;
        }
        @Override
        public String toString() {
            return code;
        }
    };

    enum MenuType {

        MAIN_MENU("1", "应用模块"),
        SUB_MENU("2", "功能模块"),
        FUNCTION_TAB("3", "功能——页签"),
        FUNCTION_BUTTON("4", "功能——按钮");

        private final String code;

        private final String name;

        private MenuType(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        @Override
        public String toString() {
            return code;
        }
    }


    enum PasswordChangeMethod {
        NO_CHANGE("0", "不需修改密码"),
        INIT_CHANGE("1", "初始密码修改"),
        INVAIL_CHANGE("2", "无效密码修改");

        private final String code;

        private final String name;

        private PasswordChangeMethod(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        @Override
        public String toString() {
            return code;
        }
    };

}
