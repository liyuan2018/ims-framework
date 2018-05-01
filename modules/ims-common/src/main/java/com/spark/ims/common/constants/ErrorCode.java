package com.spark.ims.common.constants;


/**
 * Created by liyuan on 2018/4/27.
 */
public interface ErrorCode  {

    enum Common implements IMessage {

        /**
         * 未知错误-系统异常-错误码无法识别！
         */
        invalidErrorCode(100),
        /**
         * 未知错误-系统异常-bean实例化出错！
         */
        beanInstanceError(101),
        /**
         * 系统异常-资源找不到！
         */
        resourceNotFound(102),
        /**
         * 系统异常-对象转换异常:{0}
         */
        objectTransferError(103),
        /**
         * 读写相关-文件找不到异常
         */
        fileNotFound(200),
        /**
         * 读写相关-文件超出大小限制
         */
        fileSizeNotAllow(201),
        /**
         * 读写相关-上传失败
         *
         */
        uploadFailed(202),
        /**
         * 读写相关-下载失败
         */
        downloadFailed(203),
        /**
         * 读写相关-文件写失败
         */
        uploadWriteFailed(204),
        /**
         * 数据库相关-数据库连接失败！
         */
        dbConnectError(300),
        /**
         * 公共模块-值不允许为空，参数：{0}！
         */
        paramNotNull(1000),
        /**
         * 公共模块-参数值不允许为空！
         */
        invalidNotNull(1001),
        /**
         * 公共模块-参数值长度不合法！
         */
        invalidParamLength(1002),
        /**
         * 公共模块-参数值不匹配！{0}
         */
        paramNotMatch(1003),
        /**
         * 公共模块-非法数据格式！
         */
        invalidDataFormat(1004),
        /**
         * 公共模块-正则表达式匹配失败！{0}
         */
        regexNotMatch(1005),
        /**
         * 公共模块-文件格式不正确！
         */
        invalidFileFormat(1006),
        /**
         * 公共模块-缺少方法名参数！
         */
        invalidMethodArgument(1007),
        /**
         * 公共模块-{0}：Bean反序列化出错！
         */
        beanSerializeError(1008),
        /**
         * 公共模块-值必须为true，参数：{0}
         */
        paramIsTrue(1009),
        /**
         * 公共模块-值必须为true
         */
        invalidIsTrue(1010),
        /**
         * 公共模块-值必须为空，参数：{0}
         */
        paramIsNull(1011),
        /**
         * 公共模块-值必须为空
         */
        invalidIsNull(1012),
        /**
         * 公共模块-字符串必须有值，参数：{0}
         */
        paramHasLength(1013),
        /**
         * 公共模块-字符串必须有值
         */
        invalidHasLength(1014),
        /**
         * 公共模块-字符串String必须有有效值，参数：{0}
         */
        paramHasText(1015),
        /**
         * 公共模块-字符串String必须有有效值
         */
        invalidHasText(1016),
        /**
         * 公共模块-字符串{0}必须不包含字符串{1}
         */
        invalidNotContainArgs(1017),
        /**
         * 公共模块-字符串必须不包含子字符串
         */
        invalidNotContain(1018),
        /**
         * 公共模块-数组必须有值，参数：{0}
         */
        invalidArrayNotEmptyArgs(1019),
        /**
         * 公共模块-数组必须有值
         */
        invalidArrayNotEmpty(1020),
        /**
         * 公共模块-数组中每个项必须不为空，参数：{0}
         */
        invalidElementsNotNullArgs(1021),
        /**
         * 公共模块-数组中每个项必须不为空
         */
        invalidElementsNotNull(1022),
        /**
         * 公共模块-集合必须有值，参数{0}
         */
        invalidCollectionNotEmptyArgs(1023),
        /**
         * 公共模块-集合必须有值
         */
        invalidCollectionNotEmpty(1024),
        /**
         * 公共模块-Map必须有值，参数{0}
         */
        invalidMapNotEmptyArgs(1025),
        /**
         * 公共模块-Map必须有值
         */
        invalidMapNotEmpty(1026),
        /**
         * 公共模块-对象{1}必须属于类{0}
         */
        invalidIsInstanceOfArgs(1027),
        /**
         * 公共模块-对象必须属于类
         */
        invalidIsInstanceOf(1028),
        /**
         * 公共模块-类{1}必须为类{0}的子类
         */
        invalidIsAssignableArgs(1029),
        /**
         * 公共模块-类2必须为类1的子类
         */
        invalidIsAssignable(1030),
        /**
         * 公共模块-密码输入错误！
         */
        pwdNotMatch(1031),
        /**
         * 公共模块-两次输入不一致，请重新输入
         */
        fieldConfirmError(1032),
        /**
         * 权限模块-账号未注册！
         */
        userNotRegister(1101),
        /**
         * 权限模块-账号已锁定！
         */
        userLocked(1102),
        /**
         * 权限模块-会话已过期！
         */
        sessionTimeout(1103),
        /**
         * 权限模块-无权限操作！
         */
        notPermissions(1104),
        /**
         * 权限模块-账号或密码输入有误！
         */
        userPwdNotMatch(1100),
        /**
         * 公共模块-用户密码不用与历史密码重复
         */
        userPwdRepeat(1033);

        private int code;

        private String category;

        Common(int code){
            this.code = code;
            this.category = this.getClass().getSimpleName();
        }

        public int getCode(){
            return code;
        }

        public String getCategory(){return category;}
    };

}
