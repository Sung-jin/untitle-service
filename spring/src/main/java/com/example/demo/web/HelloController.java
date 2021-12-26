package com.example.demo.web;

import com.example.demo.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private HelloService helloService;

    @GetMapping
    private String hello() {
        return "hello";
    }

    @GetMapping("/{id}")
    private String getId(@PathVariable Long id) {
        return helloService.helloWithId(id);
    }
}
