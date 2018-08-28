package com.cjq.SpringBootDemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.cjq.SpringBootDemo.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Spring Boot中FreeMaker默认存放模板的路径在src/main/resources/templates
 * Spring Boot的加载顺序是META-INF.resources > resources > static > public
 * @author v.cuijq
 *
 */
@RequestMapping(value="test")
@Controller
public class TestController {

	
	@Value("${prj.title}")
	private String title;
	@Value("${prj.description}")
	private String description;
	
	
	@RequestMapping(value="hello")
	public String hello(ModelMap map) {
		map.put("title", title);
		map.put("description", description);
		return "hello";
	}

	@RequestMapping(value="json",method = RequestMethod.POST)
	public String json(@RequestBody User user) {
		System.out.println(user.toString());
		return JSONObject.toJSONString(user);
	}

}
