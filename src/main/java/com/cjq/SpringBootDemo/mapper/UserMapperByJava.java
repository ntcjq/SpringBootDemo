package com.cjq.SpringBootDemo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.cjq.SpringBootDemo.domain.User;

public interface UserMapperByJava {

	@Select("SELECT * FROM user")
	@Results({
		@Result(property = "userName",  column = "user_name"),
		@Result(property = "nickName", column = "nick_name")
	})
	List<User> getAll();
	
	@Select("SELECT * FROM user WHERE id = #{id}")
	@Results({
		@Result(property = "userName",  column = "user_name"),
		@Result(property = "nickName", column = "nick_name")
	})
	User getOne(Long id);

	@Insert("INSERT INTO user(id,user_name,nick_name) VALUES(#{id}, #{userName}, #{nickName})")
	void insert(User user);

	@Update("UPDATE user SET user_name=#{userName},nick_name=#{nickName} WHERE id =#{id}")
	void update(User user);

	@Delete("DELETE FROM user WHERE id =#{id}")
	void delete(Long id);
}
