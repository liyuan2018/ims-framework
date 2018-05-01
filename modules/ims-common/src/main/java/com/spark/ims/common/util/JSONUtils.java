package com.spark.ims.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * 描述:JSONUtils 工具包
 *
 * Created by liyuan on 2018/4/27.
 */
public class JSONUtils {

    public static Logger logger = LoggerFactory.getLogger(JSONUtils.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 对象转json
     *
     * @param o
     * @return
     * @throws
     * @modefy : lxy 2013-7-15
     */
    public static String object2Json(Object o) {
        String json = "";
        try {
            json = objectMapper.writeValueAsString(o);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return json;
    }


    /**
     * JSON TO MAP
     *
     * @param jsonStr
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map parseJSON2Map(String jsonStr) {
        Map map = new HashMap();
        //最外层解析
        JSONObject json = (JSONObject) JSONObject.parse(jsonStr);
        for (Object k : json.keySet()) {
            Object v = json.get(k);
            //如果内层还是数组的话，继续解析
            if (v instanceof JSONArray) {
                List list = new ArrayList();
                Iterator it = ((JSONArray) v).iterator();
                while (it.hasNext()) {
                    JSONObject json2 = (JSONObject) it.next();
                    list.add(parseJSON2Map(json2.toString()));
                }
                map.put(k.toString(), list);
            } else {
                map.put(k.toString(), v);
            }
        }
        return map;
    }

    /**
     * json转实体
     *
     * @param json
     * @param entityClass
     * @return
     * @throws
     * @modefy : lxy 2013-7-15
     */
    public static <T> T json2Entity(String json, Class<T> entityClass) {
        try {
            return objectMapper.readValue(json, entityClass);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * 方法的简述. JSON转换为List方法
     * <p/>
     * 方法的详细说明第一行<br>
     * 方法的详细说明第二行
     *
     * @param json
     * @param obj
     * @return 返回List<T> 对象集合
     * @throws
     */
    public  static <T> List<T> JSON2List(String json, Class<T[]> obj)
            throws Exception {
        List<T> list = null;
        ObjectMapper mapper = new ObjectMapper();
        T[] result = mapper.readValue(json, obj);
        list = Arrays.asList(result);
        return list;
    }

    /**
     * 集合生成json方法
     *
     * @param list
     * @return 返回JSON
     * @throws
     */
    public final static <T> String List2JSON(List<T> list) throws Exception {
        String json;
        ObjectMapper mapper = new ObjectMapper();
        json = mapper.writeValueAsString(list);
        return json;
    }

    public final static <K, V> String Map2JSON(Map<K, V> map) {
        String json = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writeValueAsString(map);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return json;
    }

    /**
     * 将对象转为json
     *
     * @param value
     * @return json字符串
     * @throws
     */
    public final static String Object2JSON(Object value) {
        String json = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writeValueAsString(value);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return json;
    }
}
