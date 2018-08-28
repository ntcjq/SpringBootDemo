package com.cjq.SpringBootDemo.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SecurityHandleInterceptor extends HandlerInterceptorAdapter {


    private Logger logger = LoggerFactory.getLogger(SecurityHandleInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        logger.info("MVC-preHandle:【{}】",request.getRequestURI());
        return true;
    }
}