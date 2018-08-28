package com.cjq.SpringBootDemo.controller;

import javax.annotation.Resource;

import com.cjq.SpringBootDemo.domain.Result;
import com.cjq.SpringBootDemo.util.ResultUtil;
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
	public Result sendSimpleMail(String to, String subject, String content) {
		mailService.sendSimpleMail(to, subject, content);
		return ResultUtil.success();
	}
	
}
