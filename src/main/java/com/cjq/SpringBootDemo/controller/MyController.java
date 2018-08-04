package com.cjq.SpringBootDemo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.swing.plaf.synth.SynthSpinnerUI;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.cjq.SpringBootDemo.domain.User;
import com.cjq.SpringBootDemo.util.Util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


/**
 * @RestController 的意思就是controller里面的方法都以json格式输出，不用再写什么jackjson配置的了！
 * @author v.cuijq
 *
 */
@RequestMapping("my")
@RestController
public class MyController {
	
	@Resource
	private Util util;
	
	@RequestMapping(value="{id}",method = RequestMethod.GET)
    public String index(@PathVariable String id) {
        return "Hello World " + util.getTitle() +" "+ id;
    }
	
	@RequestMapping(method = RequestMethod.POST)
    public User save() {
		User user=new User();
    	user.setUserName("小明");
    	user.setPassWord("xxxx");
        return user;
    }
	
	@RequestMapping(method = RequestMethod.PUT)
    public String update() {
    	return "UPDATE";
    }
	
	@RequestMapping(method = RequestMethod.DELETE)
    public String delete() {
		
		return "DELETE";
    }
	@RequestMapping(value="json",method = RequestMethod.POST)
    public String json(@RequestBody User user) {
		System.out.println(user.toString());
		return JSONObject.toJSONString(user);
    }
	public static void main(String[] args) {
		int i = 0;
		System.out.println(i++);
		System.out.println(i);
	}
}
