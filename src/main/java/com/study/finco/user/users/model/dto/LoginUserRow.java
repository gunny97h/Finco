package com.study.finco.user.users.model.dto;

import com.study.finco.user.users.config.FincoUserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.UUID;

public class LoginUserRow {
    private UUID userId;
    private String userName;
    private String passwordHash;
    private String status;
    private int failedLoginCount;
    private String role;

    public UUID getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        if(userId != null) {
            this.userId = UUID.fromString(userId);
        }
//        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public int getFailed_login_count() {
        return failedLoginCount;
    }
    public void setFailed_login_count(int failed_login_count) {
        this.failedLoginCount = failed_login_count;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }


    public  UserDetails toUserDetails(byte[] emailHmac) {
        boolean enabled = "ACTIVE".equalsIgnoreCase(status);
        boolean accountNonLocked = this.getFailed_login_count() < 5;
        var auth = List.of(new SimpleGrantedAuthority("ROLE_" + role));
        return new FincoUserDetails(
                this.userId,
                emailHmac,
                this.userName,
                this.passwordHash,
                enabled,
                accountNonLocked,
                auth
        );
    }
}
