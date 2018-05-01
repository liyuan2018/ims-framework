package com.spark.ims.web.handler;


import com.spark.ims.common.domain.ErrorMessage;
import com.spark.ims.common.util.StringUtils;
import com.spark.ims.core.exception.*;
import com.spark.ims.core.util.ExceptionUtils;
import com.spark.ims.core.util.I18nUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;

import java.io.IOException;

/**
 * Created by liyuan on 2018/4/26.
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private String appNo = "000-";

    /**
     * 400-拦截所有异常
     *
     * @param ex
     * @return
     * @throws IOException
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ErrorMessage handleAllException(Exception ex) {
        logger.error(ex.getMessage(), ex);
        //判断是否是流程引擎异常
        if(ExceptionUtils.isCause(BpmActivitiException.class,ex)){
            BpmActivitiException bpmActivitiException =(BpmActivitiException) ExceptionUtils.getExpectedThrowable(BpmActivitiException.class,ex);
            return new ErrorMessage(this.appNo + bpmActivitiException.getType() + bpmActivitiException.getErrorCode().getCode(), bpmActivitiException.getMsg());
        }
        return new ErrorMessage(this.appNo + "B-100",ex.getMessage());
    }

    /**
     * 400-业务异常
     *
     * @param ex
     * @return
     * @throws IOException
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public ErrorMessage handleBusinessException(BusinessException ex) throws IOException {
        logger.error(ex.getMessage(),ex);
        String errorCode =  this.appNo + ex.getType() +"101";
        if(!StringUtils.isEmpty(ex.getErrorCode())){
            errorCode = this.appNo + ex.getType() + ex.getErrorCode().getCode();
        }
        if(!StringUtils.isEmpty(ex.getData())){
            return new ErrorMessage(errorCode, ex.getMsg(),ex.getData());
        }
        else{
            return new ErrorMessage(errorCode, ex.getMsg(),ex.getSuperMessage());
        }
    }

    /**
     * 400-资源找不到异常
     *
     * @param ex
     * @return
     * @throws IOException
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ErrorMessage handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) throws IOException {
        logger.error(ex.getMessage(), ex);
        return new ErrorMessage(this.appNo + "B-"+"102",
                I18nUtils.getMessage("Common.resourceNotFound"),ex.getMessage());
    }

    /**
     * 系统异常
     *
     * @param ex
     * @return
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = SystemException.class)
    public ErrorMessage handleSystemException(SystemException ex) {
        logger.error(ex.getMessage(), ex);
        return new ErrorMessage(this.appNo + "B-"+"103",ex.getMsg(),ex.getSuperMessage());
    }

    /**
     * multipartfile异常
     *
     * @param ex
     * @return
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MultipartException.class)
    public ErrorMessage handleMultipartException(MultipartException ex) {
        logger.error(ex.getMessage(), ex);
        if(ex.getMessage().contains("SizeLimitExceededException")){
            return new ErrorMessage(this.appNo + "B-104", I18nUtils.getMessage("Common.fileSizeNotAllow"),ex.getMessage());
        }
        else{
            return new ErrorMessage(this.appNo + "B-104", I18nUtils.getMessage("Common.uploadFailed"),ex.getMessage());
        }
    }

    /**
     * spring异常
     *
     * @param ex
     * @return
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataAccessException.class)
    public ErrorMessage handleDataAccessException(DataAccessException ex) {
        logger.error(ex.getMessage(),ex);
        String errorCode =  this.appNo + "B" +"-105";
        return new ErrorMessage(errorCode, ex.getMessage());
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = SessionTimeoutException.class)
    public ErrorMessage handleSessionTimeoutSystemException(SessionTimeoutException ex) {
        logger.error(ex.getMessage(),ex);
        return new ErrorMessage(this.appNo + "B-106",ex.getMsg());
    }

    /**
     * 400-Bean validation失败,抛出异常
     *
     * @param ex
     * @return
     * @throws IOException
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) throws IOException {
        logger.error(ex.getMessage(),ex);
        return new ErrorMessage(this.appNo + "B-107",ex.getMessage());
    }

    /**
     * 400-当Json序列化，反序列化失败的，抛出异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) throws IOException {
        logger.error(ex.getMessage(),ex);
        return new ErrorMessage(this.appNo + "B-108",ex.getMessage());
    }

    /**
     * 400-参数验证异常
     *
     * @param ex
     * @return
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorMessage handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error(ex.getMessage(),ex);
        return new ErrorMessage(this.appNo + "B-109",ex.getMessage());
    }


    /**
     * 404-资源找不到异常
     *
     * @param ex
     * @return
     * @throws IOException
     */
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorMessage handleNotFoundException(ResourceNotFoundException ex) throws IOException {
        logger.error(ex.getMessage(),ex);
        return new ErrorMessage(this.appNo +"B-110" , ex.getMsg());
    }

    /**
     * 404-找不到异常
     *
     * @param ex
     * @return
     * @throws IOException
     */
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceIdentifyNotConfigException.class)
    public ErrorMessage handleResourceIdentifyNotConfigException(Exception ex) throws IOException {
        logger.error(ex.getMessage(),ex);
        return new ErrorMessage(this.appNo + "B-111",ex.getMessage());
    }

//    /**
//     * 401-认证失败
//     *
//     * @param ex
//     * @return
//     * @throws IOException
//     */
//    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
//    @ExceptionHandler(UnauthorizedException.class)
//    public ErrorMessage handleUnauthorizedException(Exception ex) throws IOException {
//        logger.error(ex.getMessage(),ex);
//        return new ErrorMessage(ex.getMessage());
//    }

//    /**
//     * 403-认证失败,禁止访问
//     *
//     * @param ex
//     * @return
//     * @throws IOException
//     */
//    @ResponseStatus(value = HttpStatus.FORBIDDEN)
//    @ExceptionHandler(AuthorizationException.class)
//    public ErrorMessage handleAuthorizationException(Exception ex) throws IOException {
//        logger.error(ex.getMessage(),ex);
//        return new ErrorMessage(ex.getMessage());
//    }
//
//    /**
//     * 403-用户不能被识别，禁止访问
//     *
//     * @param ex
//     * @return
//     * @throws IOException
//     */
//    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
//    @ExceptionHandler(UnknownAccountException.class)
//    public ErrorMessage handleUnknownAccountException(UnknownAccountException ex) throws IOException {
//        logger.error(ex.getMessage(),ex);
//        return new ErrorMessage(ex.getMessage());
//    }
//
//    /**
//     * 401-认证失败
//     *
//     * @param ex
//     * @return
//     * @throws IOException
//     */
//    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
//    @ExceptionHandler(AuthenticationException.class)
//    public ErrorMessage handleAuthenticationException(Exception ex) throws IOException {
//        logger.error(ex.getMessage(),ex);
//        return new ErrorMessage(ex.getMessage());
//    }
//
//    /**
//     * 401-令牌超时异常
//     *
//     * @param ex
//     * @return
//     * @throws IOException
//     */
//    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
//    @ExceptionHandler(ExpiredCredentialsException.class)
//    public ErrorMessage handleAuthenticationTokenException(Exception ex) throws IOException {
//        logger.error(ex.getMessage(),ex);
//        return new ErrorMessage(ex.getMessage());
//    }
//
//    /**
//     * 401-令牌认证失败
//     *
//     * @param ex
//     * @return
//     * @throws IOException
//     */
//    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
//    @ExceptionHandler(CredentialsException.class)
//    public ErrorMessage handleCredentialsExceptionException(Exception ex) throws IOException {
//        logger.error(ex.getMessage(),ex);
//        return new ErrorMessage(ex.getMessage());
//    }
//
//    /**
//     * 401-令牌认证失败
//     *
//     * @param ex
//     * @return
//     * @throws IOException
//     */
//    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
//    @ExceptionHandler(IncorrectCredentialsException.class)
//    public ErrorMessage handleIncorrectCredentialsExceptionException(Exception ex) throws IOException {
//        logger.error(ex.getMessage(),ex);
//        return new ErrorMessage(ex.getMessage());
//    }

}
