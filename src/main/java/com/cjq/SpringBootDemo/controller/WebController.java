package com.cjq.SpringBootDemo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Spring Boot中FreeMaker默认存放模板的路径在src/main/resources/templates
 * Spring Boot的加载顺序是META-INF.resources > resources > static > public
 * @author v.cuijq
 *
 */
@RequestMapping(value="web")
@Controller
public class WebController {

	
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
}
