package TTCS.Security.controller;

import TTCS.Security.dto.request.ApiResponse;
import TTCS.Security.dto.response.UserResponse;
import TTCS.Security.entity.User;
import TTCS.Security.dto.request.UserCreationRequest;
import TTCS.Security.dto.request.UserUpdateRequest;
import TTCS.Security.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    CommandGateway commandGateway;


    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

//    @PreAuthorize("hasAnyAuthority('ADMIN')")
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
//        @PreAuthorize("hasAnyAuthority('APPROVE_POST')")
//    @PreAuthorize("hasRole('CREATE_DATA')")
        @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    ApiResponse<List<UserResponse>> getUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username : {}",authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") String userId) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(userId))
                .build();
    }

    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ApiResponse.<String>builder().result("User has been deleted").build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId, request))
                .build();
    }



}
