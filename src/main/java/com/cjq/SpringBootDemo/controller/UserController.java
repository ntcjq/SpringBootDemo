package com.cjq.SpringBootDemo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cjq.SpringBootDemo.domain.User;
import com.cjq.SpringBootDemo.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {


	@Resource
	private UserService userService;
	
	@RequestMapping(value="select")
    public List<User> select(Long id) {//只接收url后面拼的参数
		List<User> list = new ArrayList<>();
		if(id == null) {
			list = userService.getAll();
		}else {
			User user = userService.getOne(id);
			list.add(user);
		}
        return list;
    }
	
	@RequestMapping(value="insert",method = RequestMethod.POST)
    public String insert(User user) {//接收body中的参数
		userService.insert(user);
        return "INSERT";
    }
	
	@RequestMapping(value="update",method = RequestMethod.PUT)
    public String update(User user) {//只接收url后面拼的参数
		userService.update(user);
    	return "UPDATE";
    }
	
	@RequestMapping(value="delete",method = RequestMethod.DELETE)
    public String delete(Long id) {//只接收url后面拼的参数
		userService.delete(id);
		return "DELETE";
    }

	@RequestMapping(value="tx",method = RequestMethod.POST)
	public String tx(User user) {
		userService.tx(user);
		return "INSERT";
	}

}
