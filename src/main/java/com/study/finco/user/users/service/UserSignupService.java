package com.study.finco.user.users.service;

import com.study.finco.user.users.mapper.UserMapper;
import com.study.finco.user.users.model.dto.SignupRequest;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserSignupService {
    private final UserMapper userMapper;//메퍼
    private final PasswordEncoder passwordEncoder;

    public UserSignupService(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void signup(SignupRequest req) {//dto
        byte[] emailBytes = req.getEmail().getBytes(java.nio.charset.StandardCharsets.UTF_8);//임시
        byte[] phoneBytes = req.getEmail().getBytes(java.nio.charset.StandardCharsets.UTF_8);//임시
        if(userMapper.existsByEmail(emailBytes) > 0){
            throw new IllegalArgumentException("존재하는 이메일 입니다.");
        }
        if(userMapper.existsByPhone(phoneBytes) > 0){
            throw new IllegalArgumentException("이미 사용중인 전화번호 입니다.");
        }
        UUID userId = UUID.randomUUID();
        String passwordHash = passwordEncoder.encode(req.getPassword());

        userMapper.insertUser(
                userId,
                req.getUserName(),
                emailBytes,
                phoneBytes,
                passwordHash
        );
    }
}
