package com.example.demo.generator;

import com.example.demo.entity.user.User;
import com.example.demo.security.AuthenticationSecurityService;
import com.example.demo.service.UserService;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Component
public class MockUserBuilder {

    @Value("${security.pbkdf2.iteration-count}")
    private int securityIterationCount;

    @Value("${security.pbkdf2.key-size}")
    private int securityKeySize;

    @Value("${security.pbkdf2.passphrase}")
    private String securityPassphrase;

    @Value("${security.pbkdf2.iv}")
    private String securityIv;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationSecurityService authenticationSecurityService;

    private final MockHttpSession session = mock(MockHttpSession.class);
    private final MockHttpServletRequest request = new MockHttpServletRequest();

    {
        MockitoAnnotations.openMocks(this);

        request.setSession(session);
        request.setRequestURI("/");
        request.setRemoteAddr("10.10.10.10");
        request.addHeader("Authorization", "xAuth");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(session.getId()).thenReturn("c3da74ad-16a7-42c9-998d-b0cbef89d748");
    }

    public User build(String loginId, String email, String password) throws Exception {
        if (loginId == null) loginId = "demo";
        if (email == null) email = "demo@demo.com";
        if (password == null) password = "password";

        return userService.save(
                User.builder()
                        .loginId(loginId)
                        .email(email)
                        .savePassword(uiEncode(password))
                        .build()
        );
    }

    public String uiEncode(String rawPassword) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            PBEKeySpec spec = new PBEKeySpec(
                    securityPassphrase.toCharArray(),
                    Hex.decode(authenticationSecurityService.saltKey()),
                    securityIterationCount,
                    securityKeySize
            );
            SecretKeySpec key = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(Hex.decode(securityIv)));

            return new String(Base64.getEncoder().encode(cipher.doFinal(rawPassword.getBytes(StandardCharsets.UTF_8))));
        } catch (Exception e) {
            return null;
        }
    }
}
