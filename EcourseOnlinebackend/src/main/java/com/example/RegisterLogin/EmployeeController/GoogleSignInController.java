package com.example.RegisterLogin.EmployeeController;
import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoogleSignInController {
    @GetMapping
    public String welcome() {
        return "Welcome to Online E-Course";
    }

    @GetMapping("/user")
    public Principal user(Principal principal) {
        System.out.println("username :"+principal.getName());
        return principal;
    }

    @GetMapping("/secured")
    public String secured() {
        return "Hello,secured!";
    }

}