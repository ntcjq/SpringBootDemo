package com.cjq.SpringBootDemo.mapper;

import java.util.List;
import java.util.Map;

import com.cjq.SpringBootDemo.domain.User;

public interface UserMapperByXml {

	List<User> getAll();
	
	User getOne(Long id);

	void insert(User user);

	void update(User user);

	void delete(Long id);
	//用于对分布式事务测试
	void insertDept(Map map);
}
