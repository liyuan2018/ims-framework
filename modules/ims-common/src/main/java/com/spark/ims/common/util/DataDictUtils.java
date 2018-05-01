package com.spark.ims.common.util;

import java.lang.reflect.Method;

/**
 * 数字字典工具类
 * Created by liyuan on 2018/4/27.
 */
public class DataDictUtils {

    /**
     * 通过枚举的class与code获得相应枚举项
     *
     * @param clz  枚举的class
     * @param code 枚举项的code
     * @param <T>  枚举泛型
     * @return 枚举项
     */
    public static <T extends Enum> T getDataDictByCode(Class<T> clz, String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        Object[] objects = clz.getEnumConstants();
        for (Object object : objects) {
            if (object.toString().equals(code)) {
                return (T) object;
            }
        }
        return null;
    }

    /**
     * 通过枚举的class与code获得相应枚举项的名称
     *
     * @param clz  枚举的class
     * @param code 枚举项的code
     * @param <T>  枚举泛型
     * @return 枚举名称
     */
    public static <T extends Enum> String getDataDictNameByCode(Class<T> clz, String code) {
        String name = null;
        if(StringUtils.isEmpty(code)){
            return name;
        }
        T dataDict = getDataDictByCode(clz, code);
        if (dataDict != null) {
            try {
                Method method = clz.getDeclaredMethod("getName");
                name = (String) method.invoke(dataDict);
            } catch (Exception e) {
                // 因为此方法适用此平台的数字字典枚举，该字典必须有getName方法
                e.printStackTrace();
            }
        }
        return name;
    }

    /**
     * 获取字典分类
     * @param clz
     * @return
     */
    public static <T extends Enum> String getDataDictType(Class<T> clz){
    	return Inflector.getInstance().underscore(clz.getSimpleName()).toUpperCase();
    }
    
}
