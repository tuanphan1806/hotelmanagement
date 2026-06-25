package com.hotel.backend.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import com.sendgrid.SendGrid;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
public class AppConfig {
    //khoi tao spring web security
    //config spring web configurer
    //khoi tao bean cho password encoder
    @Value("${spring.sendgrid.api-key}")
    private String sendgridApikey;
    @Bean
    public SendGrid sendGrid(){
        return new SendGrid(sendgridApikey);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests( request-> request
                .requestMatchers("/**").permitAll() 
                .anyRequest().authenticated())
                .sessionManagement( manager -> manager.sessionCreationPolicy(STATELESS));
        return http.build();
    }

//     .authorizeHttpRequests(request -> request
//     // Public endpoints
//     .requestMatchers(HttpMethod.POST, "/api/user/add").permitAll()
//     .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
//     .requestMatchers("/uploads/**").permitAll()

//     // Admin only
//     .requestMatchers("/api/admin/**").hasRole("ADMIN")

//     // Còn lại phải đăng nhập
//     .anyRequest().authenticated()
// )
    @Bean
    public WebSecurityCustomizer IgnoreResources() {
        return webSecurity -> webSecurity.ignoring().requestMatchers("/actuator/**", "/v3/**", "/swagger-ui*/*swagger-initializer*", "/swagger-ui*/**","/favicon.ico");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
