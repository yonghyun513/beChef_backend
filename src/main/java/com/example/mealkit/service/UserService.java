package com.example.mealkit.service;

import com.example.mealkit.dto.UserDTO;
import com.example.mealkit.model.User;
import com.example.mealkit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<String> signup(UserDTO userDto) {
        if (userRepository.existsByUserId(userDto.getUserId())) {
            return ResponseEntity.status(400).body("아이디가 이미 존재합니다.");
        }
        String encodedPassword = passwordEncoder.encode(userDto.getPwd());
        User user = User.builder()
                .name(userDto.getName())
                .userId(userDto.getUserId())
                .pwd(encodedPassword)
                .email(userDto.getEmail())
                .phone(userDto.getPhone())
                .address(userDto.getAddress())
                .role(userDto.getRole())  // UserDTO에서 role을 가져옵니다.
                .build();
        userRepository.save(user);
        return ResponseEntity.status(201).body("회원가입 성공");
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with userId: " + userId);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUserId(),
                user.getPwd(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }

    public List<UserDTO> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> UserDTO.builder()
                .name(user.getName())
                .userId(user.getUserId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .role(user.getRole())
                .build()).collect(Collectors.toList());
    }
}