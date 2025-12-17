package com.study.finco.user.users.config;

import com.study.finco.user.common.crypto.CryptoUtil;
import com.study.finco.user.users.mapper.UserMapper;
import com.study.finco.user.users.model.dto.LoginUserRow;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.Session;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class LoginFailureHandler implements AuthenticationFailureHandler {
    private final UserMapper userMapper;
    private final CryptoUtil cryptoUtil;
    private final int threshold;

    public LoginFailureHandler(UserMapper userMapper, CryptoUtil cryptoUtil, int threshold) {
        this.userMapper = userMapper;
        this.cryptoUtil = cryptoUtil;
        this.threshold = threshold;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        if(!(exception instanceof LockedException)){
            String email = request.getParameter("email");
            String normalized = email.trim().toLowerCase();
            byte[] emailHmac = cryptoUtil.hmac(normalized);
            userMapper.increaseFailedLoginAndLockIfNeeded(emailHmac, threshold);//계정 로그인 실패 로직 구현
            System.out.println("정상작동 로그인 실패");
        }

        response.sendRedirect("/users/login?error");
    }

}
