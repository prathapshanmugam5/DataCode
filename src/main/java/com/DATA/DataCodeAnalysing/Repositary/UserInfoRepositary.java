package com.DATA.DataCodeAnalysing.Repositary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DATA.DataCodeAnalysing.Entity.UserInfo;

@Repository
public interface UserInfoRepositary extends JpaRepository<UserInfo, Long> {
	
	
	public UserInfo findByUsername(String username);

	public UserInfo findByEmail(String username);


}
