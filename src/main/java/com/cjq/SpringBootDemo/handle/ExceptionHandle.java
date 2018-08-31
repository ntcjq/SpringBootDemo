package com.cjq.SpringBootDemo.handle;

import com.cjq.SpringBootDemo.domain.Result;
import com.cjq.SpringBootDemo.exception.MyException;
import com.cjq.SpringBootDemo.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);
    @ExceptionHandler
    @ResponseBody
    public Result handle(Exception e){
        if(e instanceof MyException){
            MyException myException = (MyException)e;
            logger.error("【自定义异常】{}",myException.getMessage());
            return ResultUtil.faild(myException.getCode(),myException.getMessage());
        }
        logger.error("【系统异常】{}",e.getMessage());
        e.printStackTrace();
        return ResultUtil.faild(500,e.getMessage());
    }

}
