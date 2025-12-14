package com.study.finco.user.users.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.UUID;

@Mapper
public interface UserMapper {
    int existsByEmail(@Param("email") byte[] email);
    int existsByPhone(@Param("phoneNumber") byte[] phoneNumber);

    int insertUser(
            @Param("userId") UUID userId,
            @Param("userName") String userName,
            @Param("email") byte[] email,
            @Param("phoneNumber") byte[] phoneNumber,
            @Param("passwordHash") String passwordHash
    );
}

