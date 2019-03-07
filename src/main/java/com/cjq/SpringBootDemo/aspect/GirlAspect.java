package com.cjq.SpringBootDemo.aspect;


import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
public class GirlAspect {

    private Logger logger = LoggerFactory.getLogger(GirlAspect.class);

    @Pointcut(value="execution(* com.cjq.SpringBootDemo.controller.GirlController.*(..))")
    public void log(){
    }

    @Before("log()")
    public void logBefore(JoinPoint joinPoint){
        ServletRequestAttributes requestAttributes=  (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest)requestAttributes.getRequest();
        logger.info("url={}",request.getRequestURL());
        logger.info("method={}",request.getMethod());
        logger.info("ip={}",request.getRemoteAddr());
        logger.info("class_method={}",joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
        logger.info("args={}",joinPoint.getArgs());


    }

    /**
     * 在return之前，不管正常结束还是异常结束都会触发
     */
    @After("log()")
    public void logAfter(){
        logger.info("after log");
    }

    /**
     * 在return之后触发
     * @param object
     */
    @AfterReturning(returning = "object",pointcut = "log()")
    public void logAfterReturning(Object object){
        logger.info("AfterReturning response={}",object.toString());
    }

    /**
     * 在return抛出异常时触发
     * @param e
     */
    @AfterThrowing(throwing = "e",pointcut = "log()")
    public void logAfterReturning(Exception e){
        logger.info("AfterThrowing Msg={}",e.getMessage());
    }
}
