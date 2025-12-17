package com.study.finco.user.users.service;


import com.study.finco.user.common.crypto.CryptoUtil;
import com.study.finco.user.users.mapper.UserMapper;
import com.study.finco.user.users.model.dto.LoginUserRow;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserMapper userMapper;
    private final CryptoUtil cryptoUtil;

    public CustomUserDetailsService(UserMapper userMapper, CryptoUtil cryptoUtil) {
        this.userMapper = userMapper;
        this.cryptoUtil = cryptoUtil;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String normalized = username.trim().toLowerCase();
        byte[] emailHmac = cryptoUtil.hmac(normalized);
        LoginUserRow row = userMapper.findLoginUserByEmailHmac(emailHmac);
        if (row == null) {
            throw new UsernameNotFoundException("User not found");
        }
        System.out.println(row.getUserId()+" "+row.getUserName());
        return row.toUserDetails(emailHmac);
    }

}
