package com.DATA.DataCodeAnalysing.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DATA.DataCodeAnalysing.Dto.BaseDTO;
import com.DATA.DataCodeAnalysing.Entity.UserInfo;
import com.DATA.DataCodeAnalysing.Service.UserRegisterLoginService;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/user")
public class UserRegisterLoginController {
	
	@Autowired 
	
	UserRegisterLoginService  userRegisterLoginService;
	
	
	
	@PostMapping("/register")
	public BaseDTO UserRegistration( @RequestBody UserInfo  userInfo) {
 		return userRegisterLoginService.UserRegistration(userInfo);
	}
	
	
	@PostMapping("/login")
	public BaseDTO UserLogin( @RequestBody UserInfo  userInfo) {
 		return userRegisterLoginService.UserLogin(userInfo);
	}
	

	

}
