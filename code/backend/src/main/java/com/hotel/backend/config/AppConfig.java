package com.hotel.backend.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hotel.backend.service.UserServiceDetail;
import com.sendgrid.SendGrid;

import lombok.RequiredArgsConstructor;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
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
       
            .requestMatchers(HttpMethod.POST, "/auth/logout").authenticated()
            .requestMatchers("/auth/**").permitAll()
            .requestMatchers("/actuator/**", "/v3/**", "/swagger-ui*/*swagger-initializer*", "/swagger-ui*/**", "/favicon.ico").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/chat").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/payments/vnpay/return").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/payments/vnpay/ipn").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/payments/cash").hasAnyRole("ADMIN", "STAFF")
            .requestMatchers(HttpMethod.POST, "/api/payments/refund").hasAnyRole("ADMIN", "STAFF")
            .requestMatchers(HttpMethod.GET, "/api/payments/**").authenticated()
            .requestMatchers(HttpMethod.POST, "/api/payments/create").authenticated()
            .requestMatchers(HttpMethod.GET, "/api/room-types/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/facilities/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/galleries/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/reviews/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/rooms/available").permitAll()

            // .requestMatchers(HttpMethod.GET, "/api/users/change-password").permitAll()
            // .requestMatchers(HttpMethod.PATCH,"/api/reservations").permitAll()
            // .requestMatchers("/api/payments/**").permitAll()
            .anyRequest().authenticated())
        .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
        .authenticationProvider(authenticationProvider())
        .addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(ex -> ex
            .authenticationEntryPoint(authenticationEntryPoint)
            // .accessDeniedHandler((req, res, e) -> {
            //     res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            //     res.setContentType("application/json");
            //     res.setCharacterEncoding("UTF-8");
            //     res.getWriter().write("""
            //         {"status": 403, "error": "Forbidden", "message": "%s"}
            //         """.formatted(e.getMessage()));
            // })
        );
    return http.build();
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
