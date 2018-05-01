package com.spark.ims.common.util;

/**
 * Created by jocelynsuebb on 2016/1/13.
 */

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

public class PropUtil {

    public static final int TYPE_STRING = 0;
    public static final int TYPE_INTEGER = 1;
    public static final int TYPE_BOOLEAN = 2;
    public static final int TYPE_FLOAT = 3;
    public static final int TYPE_DOUBLE = 4;
    public static final int TYPE_LONG = 5;
    public static final int TYPE_ARRAYLIST = 6;
    public static final int TYPE_HASHMAP = 7;
    public static final int TYPE_DATE = 8;
    public static final Map<String,Integer> typeMap = new HashMap<String, Integer>();

    static {
        typeMap.put("String", TYPE_STRING);
        typeMap.put("Integer", TYPE_INTEGER);
        typeMap.put("int", TYPE_INTEGER);
        typeMap.put("Boolean", TYPE_BOOLEAN);
        typeMap.put("boolean", TYPE_INTEGER);
        typeMap.put("Float",TYPE_FLOAT);
        typeMap.put("float",TYPE_FLOAT);
        typeMap.put("Double", TYPE_DOUBLE);
        typeMap.put("double",TYPE_DOUBLE);
        typeMap.put("Long",TYPE_LONG);
        typeMap.put("long", TYPE_LONG);
        typeMap.put("ArrayList", TYPE_ARRAYLIST);
        typeMap.put("HashMap", TYPE_HASHMAP);
        typeMap.put("Date", TYPE_DATE);

    }

    //把value的类型变为field的类型
    // double -> int
    // int -> String
    // String -> int (抛出装换异常)
    // double -> String
    public static Object getRightType(Object value,Field field) throws Exception{

        int valueType = getObjectType(value);
        int fieldType = getFieldType(field);
        Object result = value;
        //两者数据类型一致
        if(valueType==fieldType){
            result = value;
        }
        //值类型为double,属性类型为integer
        else if(valueType==TYPE_DOUBLE&&fieldType==TYPE_INTEGER){
            result = new Double((Double) value).intValue();
        }
        //如果value是double型,field是String型
        else if(valueType==TYPE_DOUBLE&&fieldType==TYPE_STRING){
            String mvalue =  String.valueOf(value);
            result = mvalue.substring(0,mvalue.lastIndexOf("."));
        }
        else if(valueType==TYPE_STRING&&fieldType==TYPE_DATE){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            result = sdf.parse((String) value);
        }
        return result;
    }

    public static int getObjectType(Object o){
        String type = o.getClass().getSimpleName();
        return typeMap.get(type);
    }

    public static int getFieldType(Field o){
        String type = o.getType().getSimpleName();
        return typeMap.get(type);
    }


    public <T>  T getInstance(T t) throws Exception{
            @SuppressWarnings("unchecked")
            Class<T> cls = (Class<T>) Class.forName(t.getClass().getName());
            return cls.newInstance();
    }

    /**
     * 将一个对象的全部属性（个别除外）复制给另一个对象
     * @param p1 源对象
     * @param p2 目标对象
     * @param field 不需要复制的属性字段
     */
/*    public static void copyPropertiesExcept(Object p1,Object p2,String...field){
        Field[] exceptFields = new Field[field.length];
        for(int i = 0;i < field.length;i++){
            try {
                exceptFields[i] = p1.getClass().getDeclaredField(field[i]);
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        Field[] allFields = getFields(p1.getClass());
        List<Field> lists = new ArrayList<Field>();
        for(int i = 0;i < allFields.length;i++){
            lists.add(allFields[i]);
        }
        for(int j = 0; j<exceptFields.length;j++){
            lists.remove(exceptFields[j]);
        }
        for(int i = 0; i < lists.size();i++){
            Field changeField = lists.get(i);
            //获取p1的属性内容
            Object value = getter(p1, changeField.getName());
            setter(p2,changeField.getName(), changeField.getType(), value);
        }
    }*/

    /**
     * 获取Class类对象c的所有属性
     * @param c  Class类对象
     * @return 所有属性
     */
    public static Field[] getFields(Class<?> c){
        Field[] fields = c.getDeclaredFields();
        return fields;
    }
    /**
     * 调用对象o的setter方法
     * @param o 要设置的对象
     * @param attr 要设置属性的名称
     * @param type 属性的类型
     * @param value 要设置给属性的内容
     */
    public static void setter(Object o,String attr,Class<?>type,Object value) throws Exception{
        Method method = o.getClass().getMethod("set"+initAttr(attr), type);
        method.invoke(o,value);
    }
    /**
     * 获取对象o的attr属性内容
     * @param o 表示对象
     * @param attr 表示属性名称
     * @return 表示对象o的属性内容
     */
    public static Object getter(Object o,String attr) throws Exception{
        Method method = null;
        try {
            method = o.getClass().getMethod("get"+initAttr(attr));
            return method.invoke(o);
        } catch (Exception e) {
            throw e;
        }
    }
    /**
     * 将字符串的第一个字母换成大写，其他不变
     * @param attr 要改变的字符串
     * @return 表示修改后的字符串
     */
    public static String initAttr(String attr){
        return attr.toUpperCase().charAt(0)+attr.substring(1);
    }


    public static <T> List<Field> getAllFields(Class<T> cls){
        Field[] fields = cls.getDeclaredFields();
        Field[] superFields = cls.getSuperclass().getDeclaredFields();


        List<Field> result = new ArrayList<Field>();
        List<Field> lists = Arrays.asList(fields);
        List<Field> lists2 = Arrays.asList(superFields);

        if(cls.getSuperclass().getSuperclass()!=null){
            Field[] superFields2 = cls.getSuperclass().getSuperclass().getDeclaredFields();
            List<Field> lists3 = Arrays.asList(superFields2);
            result.addAll(lists3);
        }
        result.addAll(lists);
        result.addAll(lists2);
        return result;
    }

    public static <T> boolean existField(Class<T> cls,Field field){
        List<Field> fields = getAllFields(cls);

        List<String> fieldsName = new ArrayList<String>();
        for(Field f:fields){
            fieldsName.add(f.getName());
        }

        return fieldsName.contains(field.getName());
    }



}

