package com.DATA.DataCodeAnalysing.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
		http.csrf().disable()
		.authorizeRequests()
		.requestMatchers("customdata/getdata","user/register","user/login","images/upload").permitAll()
		.anyRequest().authenticated()
		.and().httpBasic().and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		return http.build();
	}

	@Bean
	public BCryptPasswordEncoder PasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
