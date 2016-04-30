package org.feargus.springmaster.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/*
 * See http://docs.spring.io/spring-security/site/docs/4.0.4.RELEASE/reference/htmlsingle/#hello-web-security-java-configuration
 */

//@formatter:off
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	log.info("Came into here to auth the user.");
	auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
    }

    protected void configure(HttpSecurity http) throws Exception {
	http.authorizeRequests()
		.antMatchers("/", "/index", "/accountCreation", "/accountActivation").permitAll()// Exclude some urls from requiring authentication
		.antMatchers("/admin/**").hasRole("ADMIN")// Only users witht the admin role can access urls with ../admin/..
		.antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")// Only users with both the admin and dba roles can access urls with ../db/..
		.anyRequest().authenticated()
		.and()
	.formLogin()
		.loginPage("/login")
			.permitAll()
			.and()
		.logout()
			.permitAll()
			.and()
	.httpBasic();
    }
}

// @formatter:on
/*
 * @Configuration
 * 
 * @EnableWebMvcSecurity public class SecurityConfig extends
 * WebSecurityConfigurerAdapter {
 * 
 * @Override protected void configure(HttpSecurity http) throws Exception {
 * http.authorizeRequests().antMatchers("/", "/index", "/accountCreation",
 * "/accountActivation")
 * .permitAll().anyRequest().authenticated().and().formLogin
 * ().loginPage("/login").permitAll() .and().logout().permitAll(); } }
 */