package com.ra.Controller;

import com.ra.sercurity.UserDetail.UserPrincipal;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("")
    public String index() {
        return "redirect:/admin/";
    }

    @GetMapping("/")
    public String home() {
        return "admin/index";
    }
}
