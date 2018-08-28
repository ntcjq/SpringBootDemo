package com.cjq.SpringBootDemo.domain;

import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	private String userName;
	private String passWord;
	private String email;
	private String nickName;
	private String regTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getRegTime() {
		return regTime;
	}
	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public User() {
		
	}
	public User(String userName, String passWord, String email, String nickName, String regTime) {
		super();
		
		this.userName = userName;
		this.passWord = passWord;
		this.email = email;
		this.nickName = nickName;
		this.regTime = regTime;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", passWord=" + passWord + ", email=" + email
				+ ", nickName=" + nickName + ", regTime=" + regTime + "]";
	}

	

}
