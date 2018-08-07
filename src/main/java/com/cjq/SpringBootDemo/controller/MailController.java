package com.cjq.SpringBootDemo.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cjq.SpringBootDemo.service.MailService;

@RequestMapping("mail")
@Controller
public class MailController {

	@Resource(name="mailService")
	private MailService mailService;
	
	
	@RequestMapping("send")
	@ResponseBody
	public String sendSimpleMail(String to, String subject, String content) {
		mailService.sendSimpleMail(to, subject, content);
		return "mail";
	}
	
}
