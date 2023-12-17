package com.civilMarriageSystem.Controller;

import javax.validation.Valid;

import com.civilMarriageSystem.Repositories.UserRepository;
import com.civilMarriageSystem.Config.SecurityConfiguration;
import com.civilMarriageSystem.Domain.User;
import com.civilMarriageSystem.Services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    
    @Autowired
    private UserService service;
   
    @Autowired
	private SecurityConfiguration bCryptPasswordEncoder;
    @Autowired
    private UserRepository repo;
    @RequestMapping("/")
    public String viewLogin(){
        return "login";
    }

    @RequestMapping("/login_page")
    public String viewLoginPage(){
        return "login";
    }

    @RequestMapping("/new_account")
    public String viewCreateAccountPage(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @RequestMapping(value = "/account_reg", method = RequestMethod.POST)
    public String saveUser(@Valid @ModelAttribute("user") User user, Model model) {

    try {

    if (service.appUserEmailExists(user.getEmail())) {

        model.addAttribute("message","Email Already Taken!");
        return "register";

    } else
        user.setRoles("ROLE_USER");
    user.setPassword(bCryptPasswordEncoder.getPasswordEncoder().encode(user.getPassword()));

    user.setActive(true);
    service.save(user);

            }   catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }
        model.addAttribute("message", "Account successfully created! You can login now!");
return "login";
    }

    @GetMapping("citizens/list")
    public String showClientsList(Model model) {
        model.addAttribute("citizensList", repo.getCitizens());
        return "./dashboard/citizens";
    }
}
