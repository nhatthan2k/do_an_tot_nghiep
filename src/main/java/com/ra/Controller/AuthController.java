package com.ra.Controller;

import com.ra.model.entity.Users;
import com.ra.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String Login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        Users user = new Users();
        model.addAttribute("user", user);
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") Users user) {
        userService.handleRegister(user);
        return "redirect:/login";
    }
}
