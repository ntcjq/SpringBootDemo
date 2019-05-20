package com.cjq.SpringBootDemo.config.auto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("auto")
public class HelloController {


    @Autowired
    private HelloService helloService;

    @RequestMapping("hello")
    @ResponseBody
    public String sayHello(){
        return helloService.say();
    }
}
