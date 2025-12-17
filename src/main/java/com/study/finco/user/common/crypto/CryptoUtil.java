package com.study.finco.user.common.crypto;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class CryptoUtil {
    private static final String AES_ALGO = "AES/GCM/NoPadding";
    private static final int GCM_TAG_BITS = 128;
    private static final int IV_LEN = 12; // 권장 12바이트

    private final SecretKey aesKey;
    private final SecretKey hmacKey;
    private final SecureRandom random = new SecureRandom();

    // 키는 코드에 박지 말고 환경변수/시크릿에서 주입해 (Base64로 보관 추천)
    public CryptoUtil(String aesKeyBase64, String hmacKeyBase64) {
        byte[] aes = Base64.getDecoder().decode(aesKeyBase64);// 키는 문자 -> 바이트로 변경
        byte[] hmac = Base64.getDecoder().decode(hmacKeyBase64);// 키는 문자 -> 바이트로 변경

        this.aesKey = new SecretKeySpec(aes, "AES");
        this.hmacKey = new SecretKeySpec(hmac, "HmacSHA256");
    }
    public byte[] encrypt(String plain) {//암호화
        try {
            byte[] iv = new byte[IV_LEN];
            random.nextBytes(iv);

            Cipher cipher = Cipher.getInstance(AES_ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, aesKey, new GCMParameterSpec(GCM_TAG_BITS, iv));

            byte[] cipherText = cipher.doFinal(plain.getBytes(StandardCharsets.UTF_8));//테그 생성 키 + 암호문(이메일) + iv

            ByteBuffer buf = ByteBuffer.allocate(iv.length + cipherText.length);
            buf.put(iv);
            buf.put(cipherText);
            return buf.array();
        } catch (Exception e) {
            throw new RuntimeException("암호화 오류", e);
        }
    }
    public String decrypt(byte[] enc) {//복호화
        try {
            ByteBuffer buf = ByteBuffer.wrap(enc);
            byte[] iv = new byte[IV_LEN];
            buf.get(iv);

            byte[] cipherText = new byte[buf.remaining()];
            buf.get(cipherText);

            Cipher cipher = Cipher.getInstance(AES_ALGO);
            cipher.init(Cipher.DECRYPT_MODE, aesKey, new GCMParameterSpec(GCM_TAG_BITS, iv));

            byte[] plain = cipher.doFinal(cipherText);
            return new String(plain, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("복호화 오류", e);
        }
    }

    public  byte[] hmac(String normalized){
        try{
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(hmacKey);
            return mac.doFinal(normalized.getBytes(StandardCharsets.UTF_8));
        }
        catch (Exception e) {
            throw new RuntimeException("해쉬 오류",e);
        }
    }

}
