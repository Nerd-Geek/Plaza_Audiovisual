package org.example.config.security;

import lombok.RequiredArgsConstructor;
import org.example.config.APIConfig;
import org.example.config.security.jwt.JwtAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/users/").hasAnyRole("NORMAL", "ADMIN")
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/users/{id}").hasAnyRole("NORMAL", "ADMIN")
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/users/name/{name}").hasAnyRole("NORMAL", "ADMIN")
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/users/email/{email}").hasAnyRole("NORMAL", "ADMIN")
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/users/me").hasAnyRole("NORMAL", "ADMIN")
                .antMatchers(HttpMethod.PUT, APIConfig.API_PATH + "/users/me").hasAnyRole("NORMAL", "ADMIN")
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/users/login").permitAll()
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/users/").permitAll()
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/users/create").permitAll()
                .antMatchers(HttpMethod.PUT, APIConfig.API_PATH + "/users/{id}").hasAnyRole("NORMAL", "ADMIN")
                .antMatchers(HttpMethod.DELETE, APIConfig.API_PATH + "/users/{id}").hasAnyRole("NORMAL", "ADMIN")
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/files/{filename:.+}").permitAll()
               // .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/files/{filename:.+}").hasAnyRole("NORMAL","ADMIN")
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/files/").hasAnyRole("NORMAL","ADMIN")
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/logins/token/{token}").hasAnyRole("NORMAL","ADMIN")
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/logins/").permitAll()
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/media/create").hasAnyRole("NORMAL","ADMIN")
                .anyRequest().authenticated();
        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}