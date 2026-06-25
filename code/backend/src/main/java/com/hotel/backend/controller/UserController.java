package com.hotel.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * REST Controller cho User.
 *
 * Base URL: /api/user
 *  
 * 
 */

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.hotel.backend.dto.request.UserCreationRequest;
import com.hotel.backend.dto.request.UserPasswordRequest;
import com.hotel.backend.dto.request.UserUpdateRequest;
import com.hotel.backend.dto.response.UserPageResponse;
import com.hotel.backend.dto.response.UserResponse;
import com.hotel.backend.service.UserService;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j(topic = "USER-CONTROLLER")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Create User", description = "API add new user to database")
    @PostMapping("")
    public ResponseEntity<Object> CreateUser(@RequestBody @Valid UserCreationRequest request){
        Map<String,Object>result=new LinkedHashMap<>();
        result.put("status", HttpStatus.CREATED.value());
        result.put("message","User created successfully");

        result.put("data",userService.save(request));
        
        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }



    @Operation(summary = "Update User", description = "API update user to database")
    @PutMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody UserUpdateRequest request) {
            
     // set id từ path vào request
        userService.update(request,userId);
            
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "User updated successfully");
            
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Change Password", description = "API change password user")
    @PatchMapping("/change-password")
    public Map<String,Object> changePassword(@RequestBody @Valid UserPasswordRequest request){

        userService.changePassword(request);
        Map<String,Object>result=new LinkedHashMap<>();
        result.put("status", HttpStatus.NO_CONTENT.value());
        result.put("message","Password updated successfully");
        result.put("data","");
        return result;
    }

    @Operation(summary = "Delete User", description = "API delete user to database")
    @DeleteMapping("/{userId}")
    public Map<String,Object> deleteUser(@PathVariable @Min(value = 1 , message = "user id must be equal or greater than 1") Long userId){

        userService.delete(userId);
        Map<String,Object>result=new LinkedHashMap<>();
        result.put("status", HttpStatus.RESET_CONTENT.value());
        result.put("message","User deleted successfully");
        result.put("data","");
        return result;
    }
    
    @Operation(summary = "Get detail User", description = "API retrieve user detail by id")
    @GetMapping("/{userId}")
    public Map<String,Object> getUserDetail(@PathVariable @Min(value = 1 , message = "user id must be equal or greater than 1") Long userId){

        UserResponse userDetail=userService.findById(userId);
        Map<String,Object>result=new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message","Get user by id successfully");
        result.put("data",userDetail);
        return result;
    }


    @Operation(summary = "Get list User", description = "API get list")
    @GetMapping("/list")
    public Map<String,Object> getList(@RequestParam(required = false) String keyword,
                                    @RequestParam(required = false) String sort,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "20") int size){

        UserPageResponse pageResponse= userService.findAll(keyword,sort,page,size);
        Map<String,Object>result=new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message","Get user by id successfully");
        result.put("data",pageResponse);
        return result;
    }

    @GetMapping("/confirm-email")
    public void confirmEmail(@RequestParam String secretCode ,HttpServletResponse response) throws IOException{
        log.info("Confirm Email: {}", secretCode);
        try {
            //TODO check or compare secretCode
            userService.verifyEmail(secretCode);

        } catch (Exception e) {
            log.error("Confirm Email failed: {}", e.getMessage());
        } finally{
            response.sendRedirect("https://www.facebook.com");
        }
    }

}
