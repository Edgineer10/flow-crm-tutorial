package com.example.application.security;

import java.util.Collections;

import com.example.application.views.LoginView;
import com.example.application.views.list.PostListView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().requestMatchers(new AntPathRequestMatcher("/images/*.png")).permitAll();
        http.authorizeRequests().requestMatchers(new AntPathRequestMatcher("/HP/**")).permitAll();
        http.authorizeRequests().requestMatchers(new AntPathRequestMatcher("/")).permitAll();
        http.authorizeRequests().requestMatchers(new AntPathRequestMatcher("/getposts")).permitAll();
        super.configure(http);
        setLoginView(http, LoginView.class);
        http.formLogin().defaultSuccessUrl("/list",true);
    }
}