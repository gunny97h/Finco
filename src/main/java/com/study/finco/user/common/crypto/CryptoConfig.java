package com.study.finco.user.common.crypto;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({CryptoProperties.class})
public class CryptoConfig {

    @Bean
    public CryptoUtil cryptoUtil(CryptoProperties props) {
//        System.out.println("AES=" + props.aesKeyBase64());
//        System.out.println("HMAC=" + props.hmacKeyBase64());
        return new CryptoUtil(props.aesKeyBase64(),  props.hmacKeyBase64());
    }
}
