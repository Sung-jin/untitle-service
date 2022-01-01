package com.example.demo.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class AuthenticationSecurityService {

    @Value("${security.pbkdf2.iteration-count}")
    private int securityIterationCount;

    @Value("${security.pbkdf2.key-size}")
    private int securityKeySize;

    @Value("${security.pbkdf2.passphrase}")
    private String securityPassphrase;

    @Value("${security.pbkdf2.iv}")
    private String securityIv;

    private final HttpSession httpSession;

    public AuthenticationSecurityService(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public String saltKey() {
        return new String(
                Hex.encode(httpSession.getId()
                        .replace("-", "")
                        .substring(0, 16)
                        .getBytes(StandardCharsets.UTF_8)
                )
        );
    };

    public String decrypt(String encryptionText) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(securityPassphrase.toCharArray(), Hex.decode(saltKey()), securityIterationCount, securityKeySize);
        SecretKeySpec key = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(Hex.decode(securityIv)));

        return new String(
                cipher.doFinal(Base64.decodeBase64(encryptionText)),
                StandardCharsets.UTF_8
        );
    }
}
