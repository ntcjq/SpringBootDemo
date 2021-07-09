package com.cjq.SpringBootDemo.config;

import com.cjq.SpringBootDemo.interceptor.SecurityHandleInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Configuration
public class WebConfiguration extends WebMvcConfigurationSupport {

    @Bean
    public SecurityHandleInterceptor SecurityHandleInterceptor() {
        return new SecurityHandleInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(SecurityHandleInterceptor());

        // 排除配置
        addInterceptor.excludePathPatterns("/error");
        addInterceptor.excludePathPatterns("/login**");

        // 拦截配置
        addInterceptor.addPathPatterns("/**");
    }

    @Bean
    public FilterRegistrationBean testFilterRegistration() {

        //将自定义Filter加入过滤链
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new MyFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("MyFilter");
        registration.setOrder(1);
        return registration;
    }

    public class MyFilter implements Filter {
        @Override
        public void destroy() {
            // TODO Auto-generated method stub
        }

        @Override
        public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain)
                throws IOException, ServletException {
            HttpServletRequest request = (HttpServletRequest) srequest;
            if (request.getRequestURI().indexOf("/druid") != -1) {
                filterChain.doFilter(srequest, sresponse);
                return;
            }
            System.out.println("this is MyFilter,url :" + request.getRequestURI());
            System.out.println("--------before filter");
            filterChain.doFilter(srequest, sresponse);
            System.out.println("--------after filter");
        }

        @Override
        public void init(FilterConfig arg0) throws ServletException {
            String paramValue = arg0.getInitParameter("paramName");
            System.out.println("--------------------init filter " + paramValue);
            // TODO Auto-generated method stub
        }

    }
}
