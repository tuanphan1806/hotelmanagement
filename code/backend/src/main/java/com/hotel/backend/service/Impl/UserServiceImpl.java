package com.hotel.backend.service.Impl;

import java.util.List;

import com.hotel.backend.constant.UserStatus;
import com.hotel.backend.constant.UserStatus;
import com.hotel.backend.dto.request.UserCreationRequest;
import com.hotel.backend.dto.request.UserPasswordRequest;
import com.hotel.backend.dto.request.UserUpdateRequest;
import com.hotel.backend.dto.response.UserResponse;
import com.hotel.backend.service.UserService;
import com.hotel.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotel.backend.entity.User;
import com.hotel.backend.exception.ResourceNotFoundException;
@Service
@Slf4j(topic = "USER-SERVICE")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    // inject your repos/mappers here via constructor (Lombok handles it)
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    @Override
    public List<UserResponse> findAll() {
        // TODO
        return List.of();
    }

    @Override
    public UserResponse findById(Long id) {
        // TODO
        return null;
    }

    @Override
    public UserResponse findByUsername(String username) {
        // TODO
        return null;
    }

    @Override
    public UserResponse findByEmail(String email) {
        // TODO
        return null;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Long save(UserCreationRequest req) {
        log.info("Saving user", req.getUsername());
        
        User user = User.builder()
           .fullName(req.getFullName())
           .username(req.getUsername())
           .email(req.getEmail())
           .phone(req.getPhone())
           .address(req.getAddress())
           .build();
           // role & status tự nhận default từ @Builder.Default

    userRepository.save(user);
    return user.getId();
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void update(UserUpdateRequest req) {
        //get user by id
        User user = getUserById(req.getId());
        //set data
        user.setFullName(req.getFullName());
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        user.setAddress(req.getAddress());
        //save to db
        userRepository.save(user);

    }

    @Override
    public void changePassword(UserPasswordRequest req) {
        User user = getUserById(req.getId());
        if(req.getPassword().equals(req.getConfirmPassword())){
            user.setPassword(passwordEncoder.encode(req.getPassword()));
        }
        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        User user = getUserById(id);
        user.setStatus(UserStatus.INACTIVE);
        userRepository.save(user);
        log.info("delete user: {}", user);
    }


    private User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found"));
    }
}