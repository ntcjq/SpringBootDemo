package com.cjq.SpringBootDemo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cjq.SpringBootDemo.dao.multipleDataSource.annotation.ChooseDataSource;
import com.cjq.SpringBootDemo.domain.User;
import com.cjq.SpringBootDemo.mapper.UserMapperByJava;
import com.cjq.SpringBootDemo.mapper.UserMapperByXml;

@Service(value="userService")
public class UserService {

	@Resource(name="userService")
	private UserService userService;
	
	private boolean useXml = true;
	
	@Resource
	private UserMapperByXml userMapperByXml;
	
	@Resource
	private UserMapperByJava userMapperByJava;
	
	public List<User> getAll(){
		if(useXml) {
			return userMapperByXml.getAll();
		}else {
			return userMapperByJava.getAll();
		}
	}
	public User getOne(Long id) {
		if(useXml) {
			return userMapperByXml.getOne(id);
		}else {
			return userMapperByJava.getOne(id);
		}
		
	}
	@Transactional
	public void insert(User user) {
		if(useXml) {
			userMapperByXml.insert(user);
		}else {
			userMapperByJava.insert(user);
		}
	}
	
	/**
	 * 测试分布式事务
	 * @param user
	 */
	@Transactional
	public void testJta() {
		User user = new User();
		user.setId(100l);
		user.setUserName("崔");
		user.setNickName("江海");
		userMapperByXml.insert(user);
		System.out.println("=================第一个库成功");
		userService.insertDept();
		System.out.println("=================第二个库成功");
		System.out.println(1/0);
	}
	@Transactional
	public void update(User user) {
		if(useXml) {
			userMapperByXml.update(user);
		}else {
			userMapperByJava.update(user);
		}
	}
	@Transactional
	public void delete(Long id) {
		if(useXml) {
			userMapperByXml.delete(id);
		}else {
			userMapperByJava.delete(id);
		}
		
	}
	
	@ChooseDataSource(value="ds_two")
	public void insertDept() {
		Map dept = new HashMap();
		dept.put("deptNo", 100);
		dept.put("deptName", "测试部门");
		userMapperByXml.insertDept(dept);
	}
	
}
