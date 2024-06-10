package com.ra.Controller;

import com.ra.model.dto.request.UserRegister;
import com.ra.repository.UserRepository;
import com.ra.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String Login() {
        return "auth/login";
    }

    @GetMapping("/loginError")
    public String setErrorMessageLogin(Model model) {
        model.addAttribute("errorMessage", "UserName or Password is not correct");
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        UserRegister user = new UserRegister();
        model.addAttribute("user", user);
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserRegister user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            redirectAttributes.addFlashAttribute("errorMessage", "userName is exits");
            return "redirect:/register";
        }

        userService.handleRegister(user);
        return "redirect:/login";
    }
}
