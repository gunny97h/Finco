package com.study.finco.user.users.mapper;

import com.study.finco.user.users.model.dto.LoginUserRow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.UUID;

@Mapper
public interface UserMapper {



    int existsByEmailHmac(@Param("emailHmac") byte[] emailHmac);
    int existsByPhoneHmac(@Param("phoneNumberHmac") byte[] phoneNumberHmac);

    int insertUser(
            @Param("userId") UUID userId,
            @Param("userName") String userName,
            @Param("emailEnc") byte[] emailEnc,
            @Param("emailHmac") byte[] emailHmac,
            @Param("phoneEnc") byte[] phoneEnc,
            @Param("phoneHmac") byte[] phoneHmac,
            @Param("passwordHash") String passwordHash
    );




    LoginUserRow findLoginUserByEmailHmac(@Param("emailHmac") byte[] emailHmac);

    int resetFailedLoginCountByUserId(@Param("userId") UUID userId);//로그인 count 증가

    int increaseFailedLoginAndLockIfNeeded(@Param("emailHmac") byte[] emailHmac , @Param("threshold") int threshold);//계정 로그인 실패 로직 구현
}

