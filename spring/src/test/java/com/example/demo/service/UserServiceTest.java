package com.example.demo.service;

import com.example.demo.config.annotation.LocalBootTest;
import com.example.demo.entity.user.User;
import com.example.demo.security.AuthenticationSecurityService;
import com.example.demo.security.CustomBCryptPasswordEncoder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@LocalBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

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

    @Autowired
    private CustomBCryptPasswordEncoder customBCryptPasswordEncoder;

    private final MockHttpSession session = mock(MockHttpSession.class);
    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final String rawPassword = "password";

    private String encodePassword;
    private User testUser;

    @BeforeAll
    void setUp() {
        MockitoAnnotations.openMocks(this);

        request.setSession(session);
        request.setRequestURI("/");
        request.setRemoteAddr("10.10.10.10");
        request.addHeader("Authorization", "xAuth");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(session.getId()).thenReturn("c3da74ad-16a7-42c9-998d-b0cbef89d748");

        this.encodePassword = uiEncode(rawPassword);
        this.testUser = User.builder()
                .loginId("demo")
                .email("email@demo.com")
                .savePassword(uiEncode(rawPassword))
                .build();
    }

    @Test
    @DisplayName("회원 가입 테스트")
    void saveUserTest() throws Exception {
        // given

        // when
        User result = userService.save(testUser);

        // then
        assertEquals(testUser.getLoginId(), result.getLoginId());
        assertEquals(testUser.getEmail(), result.getEmail());
    }

    @Test
    @DisplayName("패스워드 암호화 테스트")
    void userPasswordEncryptionTest() throws Exception {
        // given
        User user = userService.save(testUser);

        // when
        User result = userService.findById(user.getId());

        // then
        assertNotEquals(rawPassword, result.getPassword());
        assertTrue(customBCryptPasswordEncoder.matches(encodePassword, result.getPassword()));
    }

    @Test
    @DisplayName("유저 조회 테스트")
    void getUserTest() throws Exception {
        // given
        User user = userService.save(testUser);

        // when
        User result = userService.findById(user.getId());

        // then
        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getPassword(), result.getPassword());
    }

    private String uiEncode(String rawPassword) {
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
