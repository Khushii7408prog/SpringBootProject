package com.smart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration

public class myConfig  {
   
	/*@Bean
	public UserDetailsService getUserDetailsService() {
		 return new UserDetailsServiceImp();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		 return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider ()
	{
		DaoAuthenticationProvider daoAuthenticationProvider= new DaoAuthenticationProvider();
		
		daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
	return daoAuthenticationProvider;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) 
	{
		httpSecurity.csrf().disable()
		.authorizeHttpRequests()
		.requestMatchers("/")
		.permitAll()
		.and()
		.formLogin();
		return httpSecurity.build();
		
	}*/
	
	
	
	@Bean

	public UserDetailsService getUserDetailsService() {

		return new UserDetailsServiceImpl();

	}



	@Bean

	public BCryptPasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();

	}



	@Bean
	public DaoAuthenticationProvider authenticationProvider() {

		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

		daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());

		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

		return daoAuthenticationProvider;

	}

	



	// configure method

	@Bean

	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)

			throws Exception {

		return authenticationConfiguration.getAuthenticationManager();

	}

	

	@Bean

	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

	  http.authorizeHttpRequests()

	      .requestMatchers("/admin/**").hasRole("ADMIN")

	      .requestMatchers("/user/**").hasRole("USER")

	      .requestMatchers("/**").permitAll()

	      .and().formLogin()
	      
	     .loginPage("/signin")
	   .loginProcessingUrl("/dologin")
	     .defaultSuccessUrl("/user/index")
	    //.failureUrl("/login_fail")
	      .and().csrf().disable();

	//  http.formLogin().defaultSuccessUrl("/user/index", true);

	  return http.build();

	}

}
