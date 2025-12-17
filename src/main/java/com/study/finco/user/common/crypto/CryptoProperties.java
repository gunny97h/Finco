package com.study.finco.user.common.crypto;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "finco.crypto")
public record CryptoProperties<hmacKeyBase64>(
   String aesKeyBase64,
   String hmacKeyBase64
) {}
