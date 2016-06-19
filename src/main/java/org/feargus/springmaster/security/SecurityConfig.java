package org.feargus.springmaster.security;

import org.feargus.springmaster.security.auth.CustomSaltSource;
import org.feargus.springmaster.security.auth.CustomUserDetailsService;
import org.feargus.springmaster.security.crypto.CustomPasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@formatter:off
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
    private CustomPasswordEncoder pswrdEncoder;
    private DaoAuthenticationProvider daoAuthProvider;
    private CustomSaltSource saltSource;
    private CustomUserDetailsService userDetailsService;
    
    public SecurityConfig(){
	this.pswrdEncoder = new CustomPasswordEncoder();
	this.daoAuthProvider = new DaoAuthenticationProvider();
	this.saltSource = new CustomSaltSource();
	this.userDetailsService = new CustomUserDetailsService();
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	log.info("\n\n Setting up the auth manager....\n\n");
	
	/* Set up the auth provider */
	daoAuthProvider.setSaltSource(this.saltSource);
	daoAuthProvider.setUserDetailsService(this.userDetailsService);
	daoAuthProvider.setPasswordEncoder(this.pswrdEncoder);
	
	auth.authenticationProvider(daoAuthProvider);
    }

    protected void configure(HttpSecurity http) throws Exception {
	http.authorizeRequests()
		.antMatchers("/", "/index", "/accountCreation", "/accountActivation", "/error").permitAll()// Exclude some urls from requiring authentication
		.antMatchers("/home").hasRole("USER")//Only users may access the home page.
		.antMatchers("/admin/**").hasRole("ADMIN")// Only users with the admin role can access urls with ../admin/..
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