package com.skovdev.springlearn.security;

import com.skovdev.springlearn.security.api.ApiJWTAuthenticationProcessingFilter;
import com.skovdev.springlearn.security.api.ApiJWTSigninProcessingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletResponse;


@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Configuration
    public static class RestApiSecurityConfigAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private BCryptPasswordEncoder passwordEncoder;

        @Autowired
        private CustomUserDetailsService customUserDetailsService;


        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .userDetailsService(customUserDetailsService)
                    .passwordEncoder(passwordEncoder);
        }


        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .csrf()
                    .disable()

                .antMatcher("/api/**")
                .authorizeRequests()
                    .antMatchers("/api/user/signup").permitAll()
                .anyRequest()
                    .authenticated()
                .and()
                .exceptionHandling()
                    .authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .addFilter(new ApiJWTSigninProcessingFilter(authenticationManager()))
                .addFilter(new ApiJWTAuthenticationProcessingFilter(authenticationManager()))
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }

    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
