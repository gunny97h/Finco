package com.study.finco.user.users.controller;

import com.study.finco.user.users.model.dto.SignupRequest;
import com.study.finco.user.users.service.UserSignupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class AuthController {

    private UserSignupService userSignupService;

    public AuthController(UserSignupService userSignupService) {
        this.userSignupService = userSignupService;
    }
    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("signupRequest", new SignupRequest());
        return "users/signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("signupRequest") SignupRequest signupRequest, Model model) {
        try{
            userSignupService.signup(signupRequest);//오류 없음
            return "redirect:/users/login";
        }
        catch (IllegalArgumentException e){
            model.addAttribute("signupRequest", signupRequest);
            return "users/signup";
        }

    }
    @GetMapping("/login")
    public String login() {
        return "users/login";
    }


}
