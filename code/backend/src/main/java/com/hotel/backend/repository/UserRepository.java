package com.hotel.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.backend.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{
    
}
