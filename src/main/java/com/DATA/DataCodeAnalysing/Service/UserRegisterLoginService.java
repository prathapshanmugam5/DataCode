package com.DATA.DataCodeAnalysing.Service;

import org.springframework.stereotype.Service;

import com.DATA.DataCodeAnalysing.Dto.BaseDTO;
import com.DATA.DataCodeAnalysing.Entity.UserInfo;

@Service
public interface UserRegisterLoginService {

	BaseDTO UserRegistration(UserInfo userInfo);

	BaseDTO UserLogin(UserInfo userInfo);

}
