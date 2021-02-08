package com.rrps.security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private UserDetailsService userDetailsService;
    private JwtRequestFilter tokenFilter;

    @Autowired
    public SecurityConfiguration(
            @Qualifier("myUserDetailsService") UserDetailsService userDetailsService
            , JwtRequestFilter tokenFilter
    ) {
        this.userDetailsService = userDetailsService;
        this.tokenFilter = tokenFilter;
    }

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Authentication Configuration
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // make authentication based on jpa
        // here authentication will be based on userDetailsService
        // it will try loading a user based on the implementation
        // here its implemented to load a user using jpa
        auth.userDetailsService(userDetailsService);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //Authorization Configuration
    //Most restrictive to least (admin then users)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().
                authorizeRequests().
                antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("USER", "ADMIN")
                .antMatchers("/signup").permitAll()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated().and().
                exceptionHandling().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // Add the token filter before username password authentication filter
        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
