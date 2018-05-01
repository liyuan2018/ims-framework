package com.spark.ims.core.support.query;

import com.spark.ims.common.enums.MatchType;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * jpa动态查询
 * Created by liyuan on 2018/4/26.
 */
public class PropertyFilter {


    private String fieldName;
    private String otherField;
    private MatchType matchType;
    private boolean or;
    private boolean and;
    private boolean roundOr;
    private boolean roundAnd;
    private Object[] values;
    private List<PropertyFilter> filters = new ArrayList<PropertyFilter>();

    /**
     * values为具体类型值的构造函数
     *
     * @param fieldName
     *            属性名
     * @param matchType
     *            匹配类型 {@link MatchType}
     * @param values
     *            值数组，MatchType为BETWEEN类型时，长度必须是2，其他为1，值必须是具体类型的值，
     *            如果是字符串需要转换类型，见另一个构造函数
     *            {@link #PropertyFilter(String, MatchType, FieldType, Object...)}
     */
    public PropertyFilter(final String fieldName, MatchType matchType,
                          Object... values) {
        this.fieldName = fieldName;
        this.matchType = matchType;
        if (this.matchType == MatchType.BETWEEN
                && (values == null || values.length != 2)) {
            throw new IllegalArgumentException(String.format(
                    "%s属性选择MatchType.BETWEEN类型时，values参数长度必须为2", fieldName));
        }
        this.values = values;
        filters.add(this);
    }

    /**
     *
     * values值需要转换类型的构造函数
     *
     * @param fieldName
     *            属性名
     * @param matchType
     *            匹配类型 {@link MatchType}
     * @param fieldType
     *            属性的类型，value将被转换到此类型
     * @param values
     *            值数组,BETWEEN类型时，长度必须是2，其他为1，值必须是具体类型的值， 如果是字符串需要转换类型，见另一个构造函数
     *            {@link #PropertyFilter(String, MatchType, FieldType, Object...)}
     */
    public PropertyFilter(final String fieldName, MatchType matchType,
                          FieldType fieldType, Object... values) {
        this.fieldName = fieldName;
        this.matchType = matchType;
        Assert.notEmpty(values);
        if (this.matchType == MatchType.BETWEEN
                && (values == null || values.length != 2)) {
            throw new IllegalArgumentException(String.format(
                    "%s属性选择MatchType.BETWEEN类型时，values参数长度必须为2", fieldName));
        }
        for (int i = 0; i < values.length; i++) {
            this.values[i] = ConvertUtils.convert(values[i],
                    fieldType.getValue());
        }
        filters.add(this);
    }

    /**
     * 属性比较构造函数
     *
     * @param fieldName
     *            属性名
     * @param matchType
     *            条件类型
     * @param otherField
     *            其他属性
     */
    public PropertyFilter(final String fieldName, String otherField,
                          MatchType matchType) {
        this.fieldName = fieldName;
        this.matchType = matchType;
        this.otherField = otherField;
        filters.add(this);
    }

    /**
     * 获取属性名
     *
     * @return
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * 向当前filter添加一个or联合过滤条件
     *
     * @param filter
     * @return
     */
    public PropertyFilter addOrFilter(PropertyFilter filter) {
        filter.or = true;
        filters.add(filter);
        return this;
    }

    /**
     * 向当前filter添加一个or联合过滤条件，
     * <p>
     * 过滤条件将作为一个整体,即将所有条件放入括号内
     *
     * @param filter
     * @return
     */
    public PropertyFilter addRoundOrFilter(PropertyFilter filter) {
        Assert.isTrue(filter == this, "PropertyFilter不允许添加自身");
        filter.roundOr = true;
        filters.add(filter);
        return this;
    }

    /**
     * 向当前filter添加一个and联合过滤条件，
     *
     * @param filter
     * @return
     */
    public PropertyFilter addAndFilter(PropertyFilter filter) {
        Assert.isTrue(filter == this, "PropertyFilter不允许添加自身");
        filter.and = true;
        filters.add(filter);
        return this;
    }

    /**
     * 向当前filter添加一个and联合过滤条件，
     *
     * @param filter
     * @return
     */
    public PropertyFilter add(PropertyFilter filter) {
        Assert.isTrue(filter == this, "PropertyFilter不允许添加自身");
        filter.and = true;
        filters.add(filter);
        return this;
    }

    /**
     *
     * 向当前filter添加一个and联合过滤条件，
     * <p>
     * 过滤条件将作为一个整体,即将所有条件放入括号内
     *
     * @param filter
     * @return
     */
    public PropertyFilter addRoundAndFilter(PropertyFilter filter) {
        Assert.isTrue(filter == this, "PropertyFilter不允许添加自身");
        filter.roundAnd = true;
        filters.add(filter);
        return this;
    }

    /**
     * 判断该filter是否是一个or联合过滤，见{@link #addOrFilter(PropertyFilter)}
     *
     * @return
     */
    public boolean isOr() {
        return or;
    }

    /**
     * 判断该filter是否是一个and联合过滤，见{@link #addAndFilter(PropertyFilter)}
     *
     * @return
     */
    public boolean isAnd() {
        return and;
    }

    /**
     * 判断该filter是否是一个or联合过滤, 见 {@link #addRoundOrFilter(PropertyFilter)}
     *
     * @return
     */
    public boolean isRoundOr() {
        return roundOr;
    }

    /**
     * 判断该filter是否是一个and联合过滤, 见 {@link #addRoundAndFilter(PropertyFilter)}
     *
     * @return
     */
    public boolean isRoundAnd() {
        return roundAnd;
    }
    /**
     * 判断该filter是否是一个联合过滤
     * @return
     */
    public boolean isMulti() {
        return !filters.isEmpty();
    }
    /**
     * 获取属性的比较类型
     * @return
     */
    public MatchType getMatchType() {
        return matchType;
    }
    /**
     * 获取属性比较参数值集合
     * @return
     */
    public Object[] getValues() {
        return values;
    }

    /**
     * 联合filter迭代器
     * <p>
     * 不支持删除操作
     *
     * @return
     */
    public Iterator<PropertyFilter> iterator() {
        return new Iterator<PropertyFilter>() {
            private Iterator<PropertyFilter> it = filters.iterator();

            public boolean hasNext() {
                return it.hasNext();
            }

            public PropertyFilter next() {
                return it.next();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    /**
     * 联合filter作为一个过滤条件
     *
     * @param filter
     * @return
     */
    public PropertyFilter joinFilter(PropertyFilter filter) {
        Assert.isTrue(filter == this, "PropertyFilter不允许添加自身");
        filters.add(filter);
        return this;
    }
    /**
     * 其他field，两个属性比较时
     *
     * @return
     */
    public String getOtherField() {
        return otherField;
    }
    /**
     * 属性类型
     * @author sun4love
     *
     */
    public enum FieldType {
        String(String.class), Date(java.util.Date.class), Integer(Integer.class), Double(
                Double.class), Long(Long.class), Boolean(Boolean.class);
        private Class<?> clazz;

        private FieldType(Class<?> clazz) {
            this.clazz = clazz;
        }

        public Class<?> getValue() {
            return clazz;
        }
    }

}
