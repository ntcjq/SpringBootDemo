package com.cjq.SpringBootDemo.service;

import java.util.List;

import javax.annotation.Resource;

import com.cjq.SpringBootDemo.exception.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cjq.SpringBootDemo.domain.User;
import com.cjq.SpringBootDemo.mapper.UserMapperByJava;
import com.cjq.SpringBootDemo.mapper.UserMapperByXml;

@Service
public class UserService {


	private static final Logger mongoLogger = LoggerFactory.getLogger("MONGODB");
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
	@Cacheable(value = "userCache",keyGenerator = "keyGenerator")
	public User getOne(Long id) {
		mongoLogger.info("{\"select\":\"查询id为{}的用户信息\"}",id);
		if(useXml) {
			return userMapperByXml.getOne(id);
		}else {
			return userMapperByJava.getOne(id);
		}
		
	}
	@Transactional
	@CachePut(value = "userCache",key = "#user.id.toString()")
	public User insert(User user) {
		if(useXml) {
			userMapperByXml.insert(user);
		}else {
			userMapperByJava.insert(user);
		}
		return user;
	}
	@Transactional
	public User update(User user) {
		if(useXml) {
			userMapperByXml.update(user);
		}else {
			userMapperByJava.update(user);
		}
		return user;
	}
	@Transactional
	public void delete(Long id) {
		if(useXml) {
			userMapperByXml.delete(id);
		}else {
			userMapperByJava.delete(id);
		}
		
	}
	@Transactional
	public void tx(User user) {
		if(useXml) {
			userMapperByXml.insert(user);
			throw new MyException("新增成功，手动抛出异常,回滚");
		}else {
			userMapperByJava.insert(user);
		}
	}
}
