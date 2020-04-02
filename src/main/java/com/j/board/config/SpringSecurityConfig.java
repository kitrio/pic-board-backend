package com.j.board.config;

import java.util.Arrays;
import java.util.Collections;

import com.j.board.security.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
            .cors().and()
            .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
            .exceptionHandling().and()
            .authorizeRequests()
            .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
            .antMatchers("/authlogin").permitAll()
            .antMatchers("/member/signup").permitAll()
            .antMatchers("/member/info/**").permitAll()
            .antMatchers("/auth/admin/**").hasRole("ADMIN")
            .antMatchers("/auth/**").hasAnyRole("ADMIN", "USER")
            .antMatchers("/list/content/write/**").hasAnyRole("ADMIN", "USER")
            .antMatchers("/list/content/modify/**").hasAnyRole("ADMIN", "USER")
            .antMatchers("/list/content/delete/**").hasAnyRole("ADMIN", "USER")
            .antMatchers("/list/content/write/image").hasAnyRole("ADMIN", "USER")
            .antMatchers("/list/**").permitAll()
            .anyRequest().authenticated();
        
        httpSecurity
            .formLogin()
            .loginProcessingUrl("/authlogin")
            .usernameParameter("memberid")
            .passwordParameter("password")
            .successHandler(loginSuccessHandler())
            .failureHandler(loginFailureHandler())
            .permitAll().and()
            .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint());
        httpSecurity
            .logout()
            .logoutUrl("/member/logout")
            .logoutSuccessHandler(logoutSuccessHandler())
            .invalidateHttpSession(true)
            .deleteCookies("PICSESSIONID");

    }
    @Autowired
    MemberDetailsService memberDetailsService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(memberDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new LogoutSuccessHandler();
    }

    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Value("${list_allow_origin_url}")
    private String allowOriginUrl;

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList(allowOriginUrl));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}