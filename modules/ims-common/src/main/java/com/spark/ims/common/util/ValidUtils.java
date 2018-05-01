package com.spark.ims.common.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * 验证错误结果工具类
 * Created by liyuan on 2018/4/27.
 */
public class ValidUtils {

    public static void hasError(BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> errorList = result.getAllErrors();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < errorList.size(); i++) {
                ObjectError error = errorList.get(i);
                String message = (i + 1) + ",model:" + error.getObjectName() + ",field:" + ((FieldError) error).getField() + ",reason:" + error.getDefaultMessage();
                sb.append(message).append(" ");
            }

            throw new IllegalArgumentException(sb.toString());
        }
    }
}
