package com.hotel.backend.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hotel.backend.constant.Role;
import com.hotel.backend.constant.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;


@Entity
@Table(name = "users")
@Getter 
@Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class User extends AbstractEntity<Long> implements UserDetails,Serializable{

    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Column(name = "username",nullable = false,unique = true)
    private String username;
    @Column(nullable = false,unique = true)
    private String email;

    private String verificationCode;

    @Builder.Default
    private boolean emailVerified = false;
    
    @Column(nullable = true)
    private String password;
    @Column(nullable = false, unique = true)
    private String phone;
    
    @Column(columnDefinition = "TEXT")
    private String address;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Role role=Role.CUSTOMER;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private UserStatus status= UserStatus.PENDING_VERIFICATION;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY)
    private Set<Reservation> reservations;

    @Override 
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired(){
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled(){
        return UserStatus.ACTIVE.equals(status);
    }

}
