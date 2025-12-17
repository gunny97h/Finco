package com.study.finco.user.users.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;

public class FincoUserDetails extends User {
    private final UUID userId;
    private final byte[] emailHmac;

    public FincoUserDetails(UUID userId, byte[] emailHmac,
                            String username, String password,
                            boolean enabled, boolean accountNonLocked,
                            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled,
                true, true,
                accountNonLocked, authorities);

        this.userId = userId;
        this.emailHmac = emailHmac;
    }


    public UUID getUserId() {
        return userId;
    }

    public byte[] getEmailHmac() {
        return emailHmac;
    }
}
