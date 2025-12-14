package com.study.finco.user.main.controller;


import com.study.finco.user.main.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    @GetMapping("/")
    public String index() {

//        String email = mainService.getEmail();
//        System.out.println(email);

        return "index";
    }

    @GetMapping("/example")
    public String example() {
        return "/example";
    }
}
