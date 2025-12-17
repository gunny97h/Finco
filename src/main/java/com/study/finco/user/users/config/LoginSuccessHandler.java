package com.study.finco.user.users.config;

import com.study.finco.user.users.mapper.UserMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.UUID;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final UserMapper userMapper;
    public LoginSuccessHandler(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
    Object principal = authentication.getPrincipal();
    if (principal instanceof FincoUserDetails fincoUserDetails) {
        UUID userId = fincoUserDetails.getUserId();
        if(userId != null) {
            int num = userMapper.resetFailedLoginCountByUserId(userId);
            if(num == 0) {
                System.out.println("업데이트 실패");
            }
        }
    }

    response.sendRedirect("/");
    }
}
