package com.hotel.backend.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
public class AppConfig {
    //khoi tao spring web security
    //config spring web configurer
    //khoi tao bean cho password encoder
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests( request-> request
                .requestMatchers("/**").permitAll() 
                .anyRequest().authenticated())
                .sessionManagement( manager -> manager.sessionCreationPolicy(STATELESS));
        return http.build();
    }
    @Bean
    public WebSecurityCustomizer IgnoreResources() {
        return webSecurity -> webSecurity.ignoring().requestMatchers("/actuator/**", "/v3/**", "/swagger-ui*/*swagger-initializer*", "/swagger-ui*/**","/favicon.ico");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
