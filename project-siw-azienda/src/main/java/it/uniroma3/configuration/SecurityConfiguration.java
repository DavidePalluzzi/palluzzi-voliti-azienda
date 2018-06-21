package it.uniroma3.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Qualifier("dataSource")
	@Autowired
	private DataSource dataSource;
	
	private final String usersQuery = "SELECT username, password, TRUE FROM responsabile WHERE username=?";
	
	private final String rolesQuery = "SELECT username,role FROM responsabile WHERE username=?";
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)	throws Exception {
		auth.
			jdbcAuthentication()
				.dataSource(dataSource)
				.passwordEncoder(bCryptPasswordEncoder())
				.usersByUsernameQuery(usersQuery)
				.authoritiesByUsernameQuery(rolesQuery);
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)	throws Exception {
		auth.
			jdbcAuthentication()
				.dataSource(dataSource)
				.passwordEncoder(bCryptPasswordEncoder())
				.usersByUsernameQuery(usersQuery)
				.authoritiesByUsernameQuery(rolesQuery);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
		.csrf().disable()
			.authorizeRequests()
				.antMatchers("/","/login").permitAll()
				.anyRequest().permitAll()
				.anyRequest().authenticated()
				.and()
				.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/selectResponsabile")
				.and()
				.logout()
				.logoutSuccessUrl("/login")
				.permitAll();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web
	       .ignoring()
	       .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}

}
