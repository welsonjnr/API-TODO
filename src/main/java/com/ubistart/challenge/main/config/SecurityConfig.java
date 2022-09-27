package com.ubistart.challenge.main.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration @EnableWebSecurity @RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeHttpRequests().antMatchers(HttpMethod.POST,"/api/login/**", "/api/usuario/token/refresh").permitAll();

        http.authorizeHttpRequests().antMatchers(HttpMethod.POST, "/api/role/**").hasAnyAuthority( "ADMIN");
        http.authorizeHttpRequests().antMatchers(HttpMethod.GET, "/api/role/**").hasAnyAuthority( "ADMIN");

        http.authorizeHttpRequests().antMatchers(HttpMethod.POST, "/api/usuario/**").permitAll();
        http.authorizeHttpRequests().antMatchers(HttpMethod.GET, "/api/usuario/detail").hasAnyAuthority("USER", "ADMIN");
        http.authorizeHttpRequests().antMatchers(HttpMethod.GET, "/api/usuario/**").hasAnyAuthority("ADMIN");
        http.authorizeHttpRequests().antMatchers(HttpMethod.PUT, "/api/usuario/**").hasAnyAuthority("USER", "ADMIN");
        http.authorizeHttpRequests().antMatchers(HttpMethod.DELETE, "/api/usuario/**").hasAnyAuthority("ADMIN");

        http.authorizeHttpRequests().antMatchers(HttpMethod.GET, "/api/tarefa/usuario", "/api/tarefa/status/**").hasAnyAuthority("USER", "ADMIN");
        http.authorizeHttpRequests().antMatchers(HttpMethod.GET, "/api/tarefa/page").hasAnyAuthority("ADMIN");
        http.authorizeHttpRequests().antMatchers(HttpMethod.GET, "/api/tarefa/late", "/api/tarefa/ok", "/api/tarefa/canceled").hasAnyAuthority("ADMIN");
        http.authorizeHttpRequests().antMatchers(HttpMethod.POST, "/api/tarefa/**").hasAnyAuthority("USER", "ADMIN");
        http.authorizeHttpRequests().antMatchers(HttpMethod.PUT, "/api/tarefa/**").hasAnyAuthority("USER", "ADMIN");
        http.authorizeHttpRequests().antMatchers(HttpMethod.DELETE, "/api/tarefa/**").hasAnyAuthority("USER", "ADMIN");
        http.authorizeHttpRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**"
        );
    }
}
