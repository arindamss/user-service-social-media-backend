package com.inn.user.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityFilterConfig {

    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;
    
    @Autowired
    private JWTAuthenticationEntryPoint point;
    
    @Autowired
    private JWTAuthenticationFilter filter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { 
    	
    	 return http.csrf(csrf -> csrf.disable())
    			.authorizeHttpRequests(auth -> auth.requestMatchers("/user/signup").permitAll()
    					.requestMatchers("/user/login").permitAll()
    					.anyRequest().authenticated())
    			.exceptionHandling(ex -> ex.authenticationEntryPoint(point))
    			.sessionManagement(ses -> ses.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    			.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    	 
    	  
    		
/*        http.csrf().disable()
                .authorizeRequests()
                .requestMatchers("/signup", "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build(); */
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
