package com.cjq.SpringBootDemo.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cjq.SpringBootDemo.domain.User;
import com.cjq.SpringBootDemo.mapper.UserMapperByJava;
import com.cjq.SpringBootDemo.mapper.UserMapperByXml;

@Service
public class UserService {

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
			System.out.println("新增成功");
			System.out.println(1/0);
		}else {
			userMapperByJava.insert(user);
		}
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
}
