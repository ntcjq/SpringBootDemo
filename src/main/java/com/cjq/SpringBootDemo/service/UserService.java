package com.cjq.SpringBootDemo.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cjq.SpringBootDemo.domain.User;
import com.cjq.SpringBootDemo.mapper.UserMapperByJava;
import com.cjq.SpringBootDemo.mapper.UserMapperByXml;

@Service
public class UserService {

	private boolean useXml = true;
	
	@Resource
	private UserMapperByXml userMapperByXml = null;
	
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
	public void insert(User user) {
		if(useXml) {
			userMapperByXml.insert(user);
		}else {
			userMapperByJava.insert(user);
		}
	}
	public void update(User user) {
		if(useXml) {
			userMapperByXml.update(user);
		}else {
			userMapperByJava.update(user);
		}
	}
	public void delete(Long id) {
		if(useXml) {
			userMapperByXml.delete(id);
		}else {
			userMapperByJava.delete(id);
		}
		
	}
}
