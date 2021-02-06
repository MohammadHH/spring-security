package com.rrps.security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private DataSource dataSource;

    @Autowired
    public SecurityConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    // Authentication Configuration
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // make authentication based on database
        // the datasource here is injected with spring based on classpath
        // since h2 is present -> spring will choose it as dependency and inject it
        // the database is built with default schema -> i.e a user and authorities table
        auth.jdbcAuthentication().dataSource(dataSource).withDefaultSchema().withUser(
                User.withUsername("user").password("12345").roles("USER")
        ).withUser(
                User.withUsername("admin").password("12345").roles("ADMIN", "USER")
        );
    }


    //Authorization Configuration
    //Most restrictive to least (admin then users)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("USER", "ADMIN")
                .anyRequest().permitAll()
                .and().formLogin();
    }
}
