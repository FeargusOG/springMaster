package org.feargus.springmaster.security;

import javax.sql.DataSource;

import org.feargus.springmaster.crypto.CustomPasswordEncoder;
import org.feargus.springmaster.model.PostgresqlDataSource;
import org.feargus.springmaster.users.model.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
    private final String SELECT_USR_PSWRD = "SELECT email, pswrd, active FROM members WHERE email=?";
    private final String SELECT_USR_AUTH  = "SELECT userEmail, role from members_roles where userEmail=?";
    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
    private DataSource dataSource;
    private CustomPasswordEncoder pswrdEncoder;
    private DaoAuthenticationProvider daoAuthProvider;
    private CustomSaltSource saltSource;
    private CustomUserDetailsService userDetailsService;
    
    public SecurityConfig(){
	this.dataSource = new PostgresqlDataSource().getDefaultDataSource();
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
		/*.jdbcAuthentication()
		.dataSource(dataSource)
		.usersByUsernameQuery(SELECT_USR_PSWRD)
		.authoritiesByUsernameQuery(SELECT_USR_AUTH)
		.passwordEncoder(pswrdEncoder);*/
	//auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
    }

    protected void configure(HttpSecurity http) throws Exception {
	http.authorizeRequests()
		.antMatchers("/", "/index", "/accountCreation", "/accountActivation").permitAll()// Exclude some urls from requiring authentication
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