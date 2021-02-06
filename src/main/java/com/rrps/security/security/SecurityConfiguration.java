package com.rrps.security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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
        // the database is built with schema which is found in schema.sql in resource
        // directory root, data.sql is used to populate users
        // since schema in schema.sql is the default one
        // no need to till spring how to authenticate the user
        // based on the schema
        // if you need to get with different schema
        // then add the queries that tells how to get username password enable
        // and queries that tells how to get username authority
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username,password,enable from users where username=?").
                authoritiesByUsernameQuery("select username,authority from authorities where username=?");
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
