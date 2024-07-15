package com.DATA.DataCodeAnalysing.ServiceImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.DATA.DataCodeAnalysing.Dto.BaseDTO;
import com.DATA.DataCodeAnalysing.Dto.DataCodeDetails;
import com.DATA.DataCodeAnalysing.Entity.UserInfo;
import com.DATA.DataCodeAnalysing.Repositary.UserInfoRepositary;
import com.DATA.DataCodeAnalysing.Service.UserRegisterLoginService;

@Service
public class UserRegisterLoginServiceImpl implements UserRegisterLoginService {

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	UserInfoRepositary userInfoRepositary;

	@Autowired
	CustomDataServiceImpl customDataServiceImpl;

	@Override
	public BaseDTO UserRegistration(UserInfo userInfo) {

		BaseDTO baseDTO = new BaseDTO();

		BaseDTO baseDTOOne = new BaseDTO();

		DataCodeDetails dataCodeDetails = new DataCodeDetails();
		DataCodeDetails dataCodeDetailsOne = new DataCodeDetails();

		Map<String, String> placeholderMap = new HashMap<>();
		Map<String, String> placeholderMapOne = new HashMap<>();

		if (userInfo.getEmail() != "" && userInfo.getUsername() != "" && userInfo.getPassword() != "") {

			placeholderMap.put("username", "'" + userInfo.getUsername() + "'");
			placeholderMapOne.put("username", "'" + userInfo.getEmail() + "'");

			dataCodeDetails.setDataCode("GET_USER_DETAILS_BY_USERNAME");
			dataCodeDetailsOne.setDataCode("GET_USER_DETAILS_BY_EMAIL");

			dataCodeDetails.setPlaceholderKeyValueMap(placeholderMap);
			dataCodeDetailsOne.setPlaceholderKeyValueMap(placeholderMapOne);

			baseDTO = customDataServiceImpl.getDataFromDataCode(dataCodeDetails);
			baseDTOOne = customDataServiceImpl.getDataFromDataCode(dataCodeDetailsOne);

			if (baseDTO.getStatusCode() == 0 || baseDTOOne.getStatusCode() == 0) {
				baseDTO.setStatusCode(1);
				baseDTO.setErrorMessage("User Already Exists");
				baseDTO.setResponseContent(null);
				return baseDTO;
			}

			if (baseDTO.getStatusCode() == 1 && baseDTOOne.getStatusCode() == 1) {
				String hashedPassword = bCryptPasswordEncoder.encode(userInfo.getPassword());
				userInfo.setPassword(hashedPassword);

				UserInfo userDetail = userInfoRepositary.save(userInfo);

				baseDTO.setStatusCode(0);
				baseDTO.setResponseContent(userDetail);
				return baseDTO;
			}

		}

		if (userInfo.getEmail() == "" || userInfo.getUsername() == "" || userInfo.getPassword() == "") {

			baseDTO.setStatusCode(1);
			baseDTO.setErrorMessage("Please Fill All the fields");
			baseDTO.setResponseContent(null);
			return baseDTO;
		}

		return baseDTO;
	}
}
