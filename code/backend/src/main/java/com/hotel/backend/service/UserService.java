package com.hotel.backend.service;

import java.util.List;

import com.hotel.backend.dto.request.UserCreationRequest;
import com.hotel.backend.dto.request.UserPasswordRequest;
import com.hotel.backend.dto.request.UserUpdateRequest;
import com.hotel.backend.dto.response.UserResponse;

public interface UserService {
    List<UserResponse> findAll();
    UserResponse findById(Long id);
    UserResponse findByUsername(String username);
    UserResponse findByEmail(String email);
    Long save(UserCreationRequest req);
    void update(UserUpdateRequest req,Long id);
    void changePassword(UserPasswordRequest req);
    void delete(Long id);
}
