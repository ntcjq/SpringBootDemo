package com.cjq.SpringBootDemo.aspect;


import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
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


    @Around(value="log()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //Around的前置 （注意：@Around的前置在@Beofre之前执行）
        logger.info("Around 的前置增强");
        //调用目标方法
        Object proceed = joinPoint.proceed();
        //Around的后置 （注意：@Around的后置在@After之前执行）
        logger.info("Around 的后置增强");
        //如果目标方法有返回值，此处不返回，则返回值为null，所有用到返回值的地方都为null
        return proceed;
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
        if(object == null){
            logger.info("AfterReturning response=null");
        }else{
            logger.info("AfterReturning response={}",object.toString());
        }

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
