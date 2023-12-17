package com.civilMarriageSystem.Controller;

import com.civilMarriageSystem.Repositories.RequestsRepository;
import com.civilMarriageSystem.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

import java.security.Principal;

@Controller
@Slf4j
public class AuthController {
    @Autowired
    UserRepository user_repo;
    @Autowired
    RequestsRepository requests_repo;


    @RequestMapping("/dashboard")
    public String showDashboard(Model model, Principal principal){
        model.addAttribute("citizens", user_repo.findAll());
        model.addAttribute("requestsList2", requests_repo.findAll());
        return"./dashboard/index";

    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }
}
