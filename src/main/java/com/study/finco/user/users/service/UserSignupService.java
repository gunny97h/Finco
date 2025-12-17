package com.study.finco.user.users.service;

import com.study.finco.user.common.crypto.CryptoUtil;
import com.study.finco.user.users.mapper.UserMapper;
import com.study.finco.user.users.model.dto.SignupRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserSignupService {
    private final UserMapper userMapper;//메퍼
    private final PasswordEncoder passwordEncoder;
    private final CryptoUtil cryptoUtil; //

    public UserSignupService(UserMapper userMapper, PasswordEncoder passwordEncoder, CryptoUtil cryptoUtil) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.cryptoUtil = cryptoUtil;

    }

    public void signup(SignupRequest req) {//dto
//        byte[] emailBytes = req.getEmail().getBytes(java.nio.charset.StandardCharsets.UTF_8);//임시
//        byte[] phoneBytes = req.getEmail().getBytes(java.nio.charset.StandardCharsets.UTF_8);//임시
        String emailNorm = req.getEmail().trim().toLowerCase();
        String phoneNorm = req.getPhoneNumber().replaceAll("\\D", ""); // 숫자만


        byte[] emailHmac = cryptoUtil.hmac(emailNorm);
        byte[] phoneHmac = cryptoUtil.hmac(phoneNorm);
        if(userMapper.existsByEmailHmac(emailHmac) > 0){
            throw new IllegalArgumentException("존재하는 이메일 입니다.");
        }
        if(userMapper.existsByPhoneHmac(phoneHmac) > 0){
            throw new IllegalArgumentException("이미 사용중인 전화번호 입니다.");
        }
        UUID userId = UUID.randomUUID();
        String passwordHash = passwordEncoder.encode(req.getPassword());

        byte[] emailEnc =  cryptoUtil.encrypt(req.getEmail());
        byte[] phoneEnc =  cryptoUtil.encrypt(req.getPhoneNumber());
        userMapper.insertUser(
                userId,
                req.getUserName(),
                emailEnc,//복호화 할때
                emailHmac,//중복값 처리
                phoneEnc,
                phoneHmac,
                passwordHash
        );
    }
}
