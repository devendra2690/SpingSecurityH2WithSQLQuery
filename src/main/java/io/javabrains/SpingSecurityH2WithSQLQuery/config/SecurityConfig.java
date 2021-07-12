package io.javabrains.SpingSecurityH2WithSQLQuery.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	DataSource datasource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        
		// You can simple do this, but if you have Schema or DB not matching standard then you can use
		// below given SQL query approoch
		//auth.jdbcAuthentication()
            //.dataSource(datasource);  
        
        // another way is
		/*auth.jdbcAuthentication().dataSource(datasource)
		    .usersByUsernameQuery("select username, password, enabled from users where username = ?")
		    .authoritiesByUsernameQuery("select username, authority from authorities where username = ?");*/
		
		// For Custome Scehma and Custome Column name
		auth.jdbcAuthentication().dataSource(datasource)
		    .usersByUsernameQuery("select user_name as username, pass_word as password, enabled from myusers where user_name = ?")
		    .authoritiesByUsernameQuery("select user_name as username, auth_ority as authority from myauthorities where user_name = ?");

	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
	    .authorizeRequests()
	    .antMatchers("/admin").hasAuthority("ADMIN") // this because we are inserting authority via sql from data.sql
	    .antMatchers("/user").hasAnyAuthority("USER","ADMIN")
	    .antMatchers("/").fullyAuthenticated()
	    .and().logout().clearAuthentication(true).logoutSuccessUrl("/logoutSuccess")
	    .and().exceptionHandling().accessDeniedPage("/unauthorized")
	    .and().formLogin();
	}
	
	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}
}
