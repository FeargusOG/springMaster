package org.feargus.springmaster.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

//@Configuration
// @EnableWebSecurity

@Configuration
/*
 * @EnableWebMvcSecurity is deprecated.... I'm using it because it Automatically
 * adds the csrf tokens to all POST requests for me.
 */
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
// @formatter:off
    @Override
    protected void configure(HttpSecurity http) throws Exception {
	http.authorizeRequests()
		.antMatchers("/", "/index", "/accountCreation", "/accountActivation")
		.permitAll()
		.anyRequest()
		.authenticated()
		.and()
	.formLogin()
		.loginPage("/login")
		.permitAll()
		.and()
	.logout()
		.permitAll();
    }
 // @formatter:on
}