package com.hotel.backend.service.Impl;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hotel.backend.constant.UserStatus;
import com.hotel.backend.dto.request.UserCreationRequest;
import com.hotel.backend.dto.request.UserCreationWithTypeRequest;
import com.hotel.backend.dto.request.UserPasswordRequest;
import com.hotel.backend.dto.request.UserUpdateRequest;
import com.hotel.backend.dto.response.UserPageResponse;
import com.hotel.backend.dto.response.UserResponse;
import com.hotel.backend.service.EmailService;
import com.hotel.backend.service.UserService;
import com.hotel.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.hotel.backend.entity.User;
import com.hotel.backend.exception.DuplicateResourceException;
import com.hotel.backend.exception.InvalidDataException;
import com.hotel.backend.exception.ResourceNotFoundException;
@Service
@Slf4j(topic = "USER-SERVICE")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    // inject your repos/mappers here via constructor (Lombok handles it)
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public UserPageResponse findAll(String keyword,String sort, int page,int size) {
        

        //Sorting
        Sort.Order order= new Sort.Order(Sort.Direction.ASC, "id");
        if (StringUtils.hasLength(sort)) {
            Pattern pattern= Pattern.compile("^(\\w+?)(:)(.*)");//ten cot:asc desc
            Matcher matcher=pattern.matcher(sort);
            if (matcher.find()) {
                String columnName =matcher.group(1);
                if (matcher.group(3).equalsIgnoreCase("asc")) {
                    order= new Sort.Order(Sort.Direction.ASC, columnName);
                } else{
                    order= new Sort.Order(Sort.Direction.DESC, columnName);
                }
            }
        }

        // xu ly TH FE muon bat dau voi page =1
        int pageNo=0;
        if (page>0) {
            pageNo=page-1;
        }
        //Paging
        Pageable pageable= PageRequest.of(pageNo, size, Sort.by(order));

        Page<User> entityPage=null;
        if (StringUtils.hasLength(keyword)) {
            keyword="%"+keyword.toLowerCase()+"%";
            entityPage=userRepository.searchByKeyword(keyword,pageable);
        }else{
            entityPage= userRepository.findAll(pageable);
        }

        UserPageResponse response= getUserPageResponse(pageNo, size, entityPage);
        return response;
    }
    
    @Override
    public UserResponse findById(Long id) {
        User user = getUserById(id);
        return UserResponse.builder()
                .id(id)
                .fullName(user.getFullName())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .type(user.getType())
                .status(user.getStatus())
                .imageUrl(user.getImageUrl())
                .build();
    }


    @Override
    @Transactional(rollbackFor=Exception.class)
    public Long save(UserCreationRequest req) {
        log.info("Saving user", req.getUsername());

        if (userRepository.existsByUsername(req.getUsername())) {
            throw new DuplicateResourceException("User", "username", req.getUsername());
        }
    
        // Check duplicate email (nếu có)
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new DuplicateResourceException("User", "email", req.getEmail());
        }
        if (userRepository.existsByPhone(req.getPhone())) {
            throw new DuplicateResourceException("User", "phone", req.getPhone());
        }
        User user = User.builder()
           .fullName(req.getFullName())
           .username(req.getUsername())
           .email(req.getEmail())
           .phone(req.getPhone())
           .address(req.getAddress())
           .imageUrl(req.getImageUrl()) 
            .password(passwordEncoder.encode(req.getPassword()))
           .build();

        userRepository.save(user);

        //send email 

        try {
            emailService.emailVerification(req.getEmail(), req.getFullName());
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return user.getId();
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Long createUserWithType(UserCreationWithTypeRequest req) {
        log.info("Saving user", req.getUsername());

        if (userRepository.existsByUsername(req.getUsername())) {
            throw new DuplicateResourceException("User", "username", req.getUsername());
        }
    
        // Check duplicate email (nếu có)
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new DuplicateResourceException("User", "email", req.getEmail());
        }
        if (userRepository.existsByPhone(req.getPhone())) {
            throw new DuplicateResourceException("User", "phone", req.getPhone());
        }
        User user = User.builder()
           .fullName(req.getFullName())
           .username(req.getUsername())
           .email(req.getEmail())
           .type(req.getType())
           .phone(req.getPhone())
           .address(req.getAddress())
           .imageUrl(req.getImageUrl()) 
            .password(passwordEncoder.encode(req.getPassword()))
           .build();

        userRepository.save(user);
        log.info("User create with type {} successfully",user.getType());
        //send email 

        try {
            emailService.emailVerification(req.getEmail(), req.getFullName());
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return user.getId();
    }


    @Override
    @Transactional(rollbackFor=Exception.class)
    public void update(UserUpdateRequest req,Long id) {
        //get user by id
        User user = getUserById(id);
        //set data
        user.setFullName(req.getFullName());
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setType(req.getType());
        user.setPhone(req.getPhone());
        user.setAddress(req.getAddress());
        if (req.getImageUrl() != null) {
        user.setImageUrl(req.getImageUrl());     
        }
        //save to db
        userRepository.save(user);
        log.info("Update User successfully");
    }

    @Override
    public void changePassword(UserPasswordRequest req) {
        User user = getUserById(req.getId());
        if(!req.getPassword().equals(req.getConfirmPassword())){
            throw new InvalidDataException("Password and confirm password do not match");
        }
        user.setPassword(passwordEncoder.encode(req.getPassword()));
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


    //convert user entity to userResponse
    private static UserPageResponse getUserPageResponse (int page,int size, Page<User> users){
        List<UserResponse> userList = users.stream()
                .map(entity -> UserResponse.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .address(entity.getAddress())
                .type((entity.getType()))
                .status(entity.getStatus())
                .imageUrl(entity.getImageUrl())
                .build()
                ).toList();

        UserPageResponse response= new UserPageResponse();
        response.setPageNumber(page);
        response.setPageSize(size);
        response.setTotalElements(users.getTotalElements());
        response.setTotalPages(users.getTotalPages());
        response.setUsers(userList);
        return response;
    }
    

    public void verifyEmail(String secretCode) {
    User user = userRepository.findByVerificationCode(secretCode)
            .orElseThrow(() -> new RuntimeException("Invalid or expired verification code"));

    if (user.isEmailVerified()) {
        throw new RuntimeException("Email already verified");
    }

    user.setEmailVerified(true);
    user.setVerificationCode(null); // xóa code sau khi dùng
    user.setStatus(UserStatus.ACTIVE);
    userRepository.save(user);
    log.info("Email verified for user: {}", user.getEmail());
}
}