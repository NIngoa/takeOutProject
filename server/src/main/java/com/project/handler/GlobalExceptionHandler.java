package com.project.handler;

import com.project.constant.MessageConstant;
import com.project.exception.BaseException;
import com.project.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex) {
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * 捕获SQL异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        // 错误信息
        String message = ex.getMessage();
        // 账号重复
        if (message.contains("Duplicate entry")) {
            String[] s = message.split(" ");
            String username = s[2];
            // 返回账号已存在的错误信息
            return Result.error(username + MessageConstant.ACCOUNT_EXIST);
        }else {
            // 返回未知错误
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }

}
