package com.hotel.backend.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.hotel.backend.service.UserServiceDetail;
import com.sendgrid.SendGrid;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
@RequiredArgsConstructor
public class AppConfig {
    //khoi tao spring web security
    //config spring web configurer
    //khoi tao bean cho password encoder
    private final CustomizeRequestFilter requestFilter;
    private final UserServiceDetail userServiceDetail;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
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
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(request -> request
            .requestMatchers("/auth/**").permitAll()

            .requestMatchers(HttpMethod.GET, "/api/room-types/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/facilities/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/galleries/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/reviews/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/rooms/available").permitAll()

            .requestMatchers(HttpMethod.PATCH,"/api/reservations").permitAll()

            .anyRequest().authenticated())
        .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
        .authenticationProvider(authenticationProvider())
        .addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(ex -> ex
            .authenticationEntryPoint(authenticationEntryPoint)
            .accessDeniedHandler((req, res, e) -> {
                res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                res.setContentType("application/json");
                res.setCharacterEncoding("UTF-8");
                res.getWriter().write("""
                    {"status": 403, "error": "Forbidden", "message": "%s"}
                    """.formatted(e.getMessage()));
            })
        );
    return http.build();
}

    @Bean
    public WebSecurityCustomizer IgnoreResources() {
        return webSecurity -> webSecurity.ignoring().requestMatchers("/actuator/**", "/v3/**", "/swagger-ui*/*swagger-initializer*", "/swagger-ui*/**","/favicon.ico");
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        .allowCredentials(false)
                        .maxAge(3600);
            }
        };
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)throws Exception{
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider= new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userServiceDetail);
        return authProvider;
    }


    @Value("${spring.sendgrid.api-key}")
    private String sendgridApikey;
    @Bean
    public SendGrid sendGrid(){
        return new SendGrid(sendgridApikey);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
