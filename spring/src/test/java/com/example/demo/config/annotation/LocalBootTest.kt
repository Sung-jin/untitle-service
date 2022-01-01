package com.example.demo.config.annotation;

import com.example.demo.DemoApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@SpringBootTest(classes = {DemoApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("local")
public @interface LocalBootTest {}
