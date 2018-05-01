package com.spark.ims.core.support.mybatis;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * mybatis结果集特殊处理转出jpa-page对象
 * 自定义Jpa-Page实例
 * Created by liyuan on 2018/4/26.
 */
public class CustomPageImpl <T>  implements Page<T> {

    private static final long serialVersionUID = 1;
    private  long total;
    private  List<T> content = new ArrayList<T>();
    private  Pageable pageable;

    public CustomPageImpl(List<T> content) {
        Assert.notNull(content, "Content must not be null!");
        this.content.addAll(content);
    }
    @Override
    public List<T> getContent() {
        return  this.content;
    }

    @Override
    public int getTotalPages() {
        return 0;
    }

    @Override
    public long getTotalElements() {
        return 0;
    }

    @Override
    public int getNumber() {
        return 0;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public int getNumberOfElements() {
        return 0;
    }


    @Override
    public boolean hasContent() {
        return false;
    }

    @Override
    public Sort getSort() {
        return null;
    }

    @Override
    public boolean isFirst() {
        return false;
    }

    @Override
    public boolean isLast() {
        return false;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    @Override
    public Pageable nextPageable() {
        return null;
    }

    @Override
    public Pageable previousPageable() {
        return null;
    }

    @Override
    public <S> Page<S> map(Converter<? super T, ? extends S> converter) {
        return null;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }
}

