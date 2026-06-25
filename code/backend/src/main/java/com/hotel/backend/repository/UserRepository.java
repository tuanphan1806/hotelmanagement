package com.hotel.backend.repository;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hotel.backend.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{
    @Query(value = "select u from User u where lower(u.fullName) like :keyword or lower(u.username) like :keyword or lower(u.email) like :keyword or lower(u.phone) like :keyword")
    Page<User> searchByKeyword(String keyword, Pageable pageable);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    Optional<User> findByEmail(String email);
    Optional<User> findByVerificationCode(String verificationCode);
}
