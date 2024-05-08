package com.ra.Controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/checkout")
public class CheckOutController {
    @GetMapping
    public String checkoutHome() {

        return "/shop/checkout";
    }
}
