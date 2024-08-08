package com.DATA.DataCodeAnalysing.ServiceImpl;



import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.DATA.DataCodeAnalysing.Dto.BaseDTO;

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
		
		
		
		if (userInfo.getEmail() == "" || userInfo.getUsername() == "" || userInfo.getPassword() == "" || userInfo.getEmail() == null||userInfo.getUsername() == null||userInfo.getPassword() == null) {

			baseDTO.setStatusCode(1);
			baseDTO.setErrorMessage("Please Fill All the fields");
			baseDTO.setResponseContent(null);
			return baseDTO;
		}


//		DataCodeDetails dataCodeDetails = new DataCodeDetails();
//		DataCodeDetails dataCodeDetailsOne = new DataCodeDetails();
//
//		Map<String, String> placeholderMap = new HashMap<>();
//		Map<String, String> placeholderMapOne = new HashMap<>();

		if (userInfo.getEmail() != "" && userInfo.getUsername() != "" && userInfo.getPassword() != "" || userInfo.getEmail() != null||userInfo.getUsername() != null||userInfo.getPassword() != null ) {

//			placeholderMap.put("username", "'" + userInfo.getUsername() + "'");
//			placeholderMapOne.put("email", "'" + userInfo.getEmail() + "'");
//
//			dataCodeDetails.setDataCode("GET_USER_DETAILS_BY_USERNAME");
//			dataCodeDetailsOne.setDataCode("GET_USER_DETAILS_BY_EMAIL");
//
//			dataCodeDetails.setPlaceholderKeyValueMap(placeholderMap);
//			dataCodeDetailsOne.setPlaceholderKeyValueMap(placeholderMapOne);
			
			  UserInfo userDetailByEmail=userInfoRepositary.findByEmail(userInfo.getEmail());
			  UserInfo userDetailUserName=userInfoRepositary.findByUsername(userInfo.getUsername());
		        
		        if(userDetailUserName!=null) {
		        	baseDTOOne.setStatusCode(0);
		        	baseDTOOne.setErrorMessage("Login Success");
		        	baseDTOOne.setResponseContent(userDetailUserName); 
		        	
		        }
		        if(userDetailUserName==null) {
		        	baseDTOOne.setStatusCode(1);
		        	baseDTOOne.setErrorMessage("User Already Exists");
		        	baseDTOOne.setResponseContent(userDetailUserName); 
		        	
		        }
		        
		        if(userDetailByEmail!=null) {
		        	 baseDTO.setStatusCode(0);
	                 baseDTO.setErrorMessage("Login Success");
	                 baseDTO.setResponseContent(userDetailByEmail); 
		        	
		        }
		        if(userDetailByEmail==null) {
		        	 baseDTO.setStatusCode(1);
	                baseDTO.setErrorMessage("User Already Exists");
	                baseDTO.setResponseContent(userDetailByEmail); 
		        	
		        }
			

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
				baseDTO.setErrorMessage("Register Success");
				baseDTO.setResponseContent(userDetail);
				return baseDTO;
			}

		}



		return baseDTO;
	}

	@Override
	public BaseDTO UserLogin(UserInfo userInfo) {
		
	    BaseDTO baseDTO = new BaseDTO();

//	    DataCodeDetails dataCodeDetailsOne = new DataCodeDetails();
//	    Map<String, String> placeholderMapOne = new HashMap<>();

	    if (!userInfo.getEmail().isEmpty() && !userInfo.getPassword().isEmpty()) {
	    	
//	    	placeholderMapOne.put("email", "'" + userInfo.getEmail() + "'");
//	        dataCodeDetailsOne.setDataCode("GET_USER_DETAILS_BY_EMAIL");
//	        dataCodeDetailsOne.setPlaceholderKeyValueMap(placeholderMapOne);
//	        
//	        userInfo.setUsername("prathap"); 
	    
	        UserInfo userDetail=userInfoRepositary.findByEmail(userInfo.getEmail());
	        
	        if(userDetail!=null) {
	        	 baseDTO.setStatusCode(0);
                 baseDTO.setErrorMessage("Login Success");
                 baseDTO.setResponseContent(userDetail); 
                 
               
                
	        	
	        }
	        if(userDetail==null) {
	        	 baseDTO.setStatusCode(1);
                baseDTO.setErrorMessage("User Not Found");
                baseDTO.setResponseContent(userDetail); 
          
                
	        	
	        }
	        
	    

	        if (baseDTO.getStatusCode() == 0) {
	            // Assuming baseDTO.getResponseContent() returns a list of maps, where each map is a row from the database
	        

	            	               // Get the first (and only) user record
	                String storedPassword = userDetail.getPassword();

	                if (bCryptPasswordEncoder.matches(userInfo.getPassword(), storedPassword)) {
	                    baseDTO.setStatusCode(0);
	                    baseDTO.setErrorMessage("Login Success");
	                    baseDTO.setResponseContent(userDetail); // Optionally, you can set the user info as the response content
	                    return baseDTO;
	                } else {
	                    baseDTO.setStatusCode(1);
	                    baseDTO.setErrorMessage("Invalid password");
	                    baseDTO.setResponseContent(null);
	                    return baseDTO;
	                }
	            } else {
	                baseDTO.setStatusCode(1);
	                baseDTO.setErrorMessage("User does not exist");
	                baseDTO.setResponseContent(null);
	                return baseDTO;
	            }
	        }

	    

	    if (userInfo.getEmail().isEmpty() || userInfo.getPassword().isEmpty()) {
	        baseDTO.setStatusCode(1);
	        baseDTO.setErrorMessage("Please fill all the fields");
	        baseDTO.setResponseContent(null);
	        return baseDTO;
	    }

	    return baseDTO;
	}

}
