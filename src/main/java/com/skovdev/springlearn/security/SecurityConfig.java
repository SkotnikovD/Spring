package com.skovdev.springlearn.security;

import com.skovdev.springlearn.security.api.ApiJWTAuthenticationProcessingFilter;
import com.skovdev.springlearn.security.api.ApiJWTSigninProcessingFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletResponse;


@EnableWebSecurity
@Configuration
@Slf4j
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
                .cors().and()
                .antMatcher("/api/**")
                .authorizeRequests()
                    .antMatchers("/api/users/signup").permitAll()
                    .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/posts").permitAll()
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
    public CorsFilter corsFilter() {

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*"); //All origins allowed. At this point, this application doesn't need CORS policies, as it will just add additional difficulties on deployment process
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
