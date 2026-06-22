package com.hotel.backend.entity;



import com.hotel.backend.constant.Role;
import com.hotel.backend.constant.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.io.Serializable;


@Entity
@Table(name = "users")
@Getter 
@Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class User extends AbstractEntity<Long> implements Serializable{

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(unique = true)
    private String email;

    private String password;
    private String phone;
    
    @Column(columnDefinition = "TEXT")
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @OneToMany(mappedBy = "customer")
    private Set<Reservation> reservations;

}
