package com.dpf.myvhr.exception;

import com.dpf.myvhr.model.RespBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author dpf
 * @create 2020-04-24 12:06
 * @email 446933040@qq.com
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLException.class)
    public RespBean sqlException(SQLException e){
        if (e instanceof SQLIntegrityConstraintViolationException) {
            return RespBean.error("该数据有关联数据，操作失败!");
        }
        System.out.println(e.getMessage());
        e.printStackTrace();
        return RespBean.error("数据库异常，操作失败!");
    }
}
