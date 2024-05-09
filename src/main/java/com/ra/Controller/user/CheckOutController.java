package com.ra.Controller.user;

import com.ra.model.entity.ShopingCart;
import com.ra.model.entity.Users;
import com.ra.service.ShopingCartService;
import com.ra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.ra.Controller.user.CartController.getUserId;

@Controller
@RequestMapping("/user/checkout")
public class CheckOutController {
    @Autowired
    private UserService userService;
    @Autowired
    private ShopingCartService shopingCartService;
    @GetMapping
    public String checkoutHome(Model model) {
        Long userId = getUserId();
        Users users = userService.findById(userId);
        List<ShopingCart> shopingCarts = shopingCartService.getAll(userId);
        double total = 0;
        for (ShopingCart item: shopingCarts) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        model.addAttribute("shopingCarts", shopingCarts);
        model.addAttribute("user", users);
        model.addAttribute("total", total);
        return "/shop/checkout";
    }
}
