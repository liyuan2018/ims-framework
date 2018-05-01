package com.spark.ims.web.converter;

import com.spark.ims.common.util.ClassUtils;
import com.spark.ims.common.util.ParamUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * ember转换器
 * Created by liyuan on 2018/4/26.
 */
public class EmberConverter extends AbstractConverter {
    private static final Logger logger = LogManager.getLogger(EmberConverter.class.getName());

    private EmberConverter() {
    }

    /**
     * 获得单例
     *
     * @return
     */
    public static EmberConverter getInstance() {
        return EmberConverterHolder.INSTANCE;
    }

    @Override
    public Object extractMap(Map map) {
        final Map<String, Object> results = new HashMap<String, Object>();
        for (Object item : map.entrySet()) {
            Map.Entry entry = (Map.Entry) item;
            if (entry.getValue().getClass().getName().startsWith(PACKAGE_TO_PROCESS)) {
                results.put(ClassUtils.getLowerCamelAndSingularize(entry.getValue().getClass().getSimpleName()), entry.getValue());
                logger.debug("处理实体: {}", entry.getValue().getClass().getSimpleName());
            } else if (entry.getValue() instanceof List) {
                List list = (List) entry.getValue();
                if (ParamUtils.isEmpty(list)) {
                    return results.put(entry.getKey().toString(), entry.getValue());
                }
                results.put(ClassUtils.getLowerCamelAndPluralize(list.get(0).getClass().getSimpleName()), list);
                logger.debug("处理List: {}", list.get(0).getClass().getSimpleName());
            } else if (entry.getValue() instanceof Set) {
                logger.debug("准备处理Set。");
                Set set = (Set) entry.getValue();
                if (set.size() == 0) {
                    return results.put(entry.getKey().toString(), entry.getValue());
                }
                Iterator iterator = set.iterator();
                while (iterator.hasNext()) {
                    results.put(ClassUtils.getLowerCamelAndPluralize(iterator.next().getClass().getSimpleName()), set);
                    logger.debug("处理Set: {}", iterator.next().getClass().getSimpleName());
                    break;
                }
            } else {
                results.put(entry.getKey().toString(), entry.getValue());
                logger.debug("处理默认情况, key: {}, value: {}", entry.getKey().toString(), entry.getValue());
            }
        }
        logger.debug("响应内容为Map, size: {}, 处理完毕。", map.size());
        return results;
    }

    @Override
    public Object extractList(List list) {
        final Map<String, Object> results = new HashMap<String, Object>();
        results.put(ClassUtils.getLowerCamelAndPluralize(list.get(0).getClass().getSimpleName()), list);
        logger.debug("响应内容为List, name: {}, 处理完毕。", list.get(0).getClass().getSimpleName());
        return results;
    }

    @Override
    public Object extractSet(Set set) {
        logger.debug("准备处理响应Set内容。");
        final Map<String, Object> results = new HashMap<String, Object>();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            results.put(ClassUtils.getLowerCamelAndPluralize(iterator.next().getClass().getSimpleName()), set);
            logger.debug("响应内容为Set, name: {}, 处理完毕。", iterator.next().getClass().getSimpleName());
            break;
        }
        return results;
    }

    @Override
    public Object extractEntity(Object object) {
        Map<String, Object> results = new HashMap<String, Object>();
        results.put(ClassUtils.getLowerCamelAndSingularize(object.getClass().getSimpleName()), object);
        logger.debug("响应内容为实体, name: {}, 处理完毕。", object.getClass().getSimpleName());
        return results;
    }

    private static class EmberConverterHolder {
        private static final EmberConverter INSTANCE = new EmberConverter();
    }
}
