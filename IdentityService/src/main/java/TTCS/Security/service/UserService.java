package TTCS.Security.service;


import TTCS.Security.dto.request.IntrospectRequest;
import TTCS.Security.dto.response.IntrospectResponse;
import TTCS.Security.dto.response.UserResponse;
import TTCS.Security.entity.Role;
import TTCS.Security.entity.User;
import TTCS.Security.exception.AppException;
import TTCS.Security.exception.ErrorCode;
import TTCS.Security.mapper.UserMapper;
import TTCS.Security.repository.RoleRepository;
import TTCS.Security.repository.UserRepository;
import TTCS.Security.dto.request.UserCreationRequest;
import TTCS.Security.dto.request.UserUpdateRequest;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
     UserRepository userRepository;

   UserMapper userMapper;
   RoleRepository roleRepository;
    @Transactional

    public UserResponse createUser(UserCreationRequest request){
        if(userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
//        Role role = roleRepository.findByName("ADMIN");
//        System.out.println(role.toString());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        User user = userMapper.toUser(request);
//        Set<Role> roles = new HashSet<>();
//        roles.add(role);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        user.setRoles(roles);
        return userMapper.toUserResponse(userRepository.save(user));
    }

@Transactional
    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        if(userRepository.findById(userId) == null) {
            throw new RuntimeException();
        }
        Role role = roleRepository.findByName("USER");
        User user = userRepository.findById(userId).orElseThrow(null);
        userMapper.updateUser(user, request);
        user.getRoles().add(role);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }
    public List<UserResponse> getUsers(){
        log.info("in method");
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> UserResponse.builder().
                id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName()).dob(user.getDob()).build()).toList();
    }

    @PostAuthorize("returnObject.username==authentication.name")
    public UserResponse getUser(String id){
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found")));
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }
}
