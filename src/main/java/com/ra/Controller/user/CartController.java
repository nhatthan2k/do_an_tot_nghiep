package com.ra.Controller.user;

import com.ra.model.dto.request.QuantityRequest;
import com.ra.model.dto.request.ShopingCartRequest;
import com.ra.model.entity.Orders;
import com.ra.model.entity.Product;
import com.ra.model.entity.ShopingCart;
import com.ra.model.entity.Users;
import com.ra.sercurity.UserDetail.UserPrincipal;
import com.ra.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user/cart")
public class CartController {
    @Autowired
    private ShopingCartService shopingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    public static Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return userPrincipal.getUsers().getId();
    }

    @GetMapping("")
    public String cartShop(Model model) {
        Long userId = getUserId();
        List<ShopingCart> shopingCarts = shopingCartService.getAll(userId);
        double total = 0;
        for (ShopingCart item: shopingCarts) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        model.addAttribute("shopingCarts", shopingCarts);
        model.addAttribute("total", total);
        return "/shop/cart";
    }

    @GetMapping("/{id}")
    public String addCart(@PathVariable("id") Long id) {
        Long userId = getUserId();
        ShopingCart shopingCart = shopingCartService.findByProductId(userId, id);
        if (shopingCart == null) {
            ShopingCartRequest shopingCartRequest = new ShopingCartRequest();
            shopingCartRequest.setProductId(id);
            shopingCartRequest.setQuantity(1);
            shopingCartService.add(shopingCartRequest, userId);
        } else {
            shopingCart.setQuantity(shopingCart.getQuantity() + 1);
            shopingCartService.save(shopingCart);
        }
        return "redirect:/user/cart";
    }

    @PostMapping("/add-cart/{id}")
    public String createCart(@PathVariable("id") Long id, @RequestParam("quantity") int quantity) {
        Long userId = getUserId();
        ShopingCart shopingCart = shopingCartService.findByProductId(userId, id);
        ShopingCartRequest shopingCartRequest = new ShopingCartRequest();
        if (shopingCart == null) {
            shopingCartRequest.setQuantity(quantity);
            shopingCartRequest.setProductId(id);
            shopingCartService.add(shopingCartRequest, userId);
        } else {
            shopingCart.setQuantity(shopingCart.getQuantity() + quantity);
            shopingCartService.save(shopingCart);
        }
        return "redirect:/user/cart";
    }

    @GetMapping("/delete/{id}")
    public String deleteCart(@PathVariable("id") Long id) {
        Long userId = getUserId();
        ShopingCart shopingCart = shopingCartService.findByProductId(userId, id);
        if (shopingCart != null) {
            shopingCartService.delete(shopingCart.getId());
        }
        return "redirect:/user/cart";
    }

    @PostMapping("/edit-cart/{id}")
    public String editCart(@PathVariable("id") int id, @RequestParam("quantity") int quantity) {
        Long userId = getUserId();
        ShopingCart shopingCart = shopingCartService.findById(id);
        if(shopingCart != null) {
            if(shopingCart.getUsers().getId().equals(userId)) {
                shopingCart.setQuantity(quantity);
                shopingCartService.save(shopingCart);
            }
        }
        return "redirect:/user/cart";
    }

    @PostMapping("/checkout")
    public String checkOut(@ModelAttribute("user") Users newUser) {
        Long userId = getUserId();
    //  lấy ra giỏ hàng của tài khoản đang đăng nhập
        List<ShopingCart> shopingCarts = shopingCartService.getAll(userId);
    //  lấy ra thông tin tài khoản đang đăng nhập
        Users user = userService.findById(userId);
        user.setFullName(newUser.getFullName());
        user.setAddress(newUser.getAddress());
        user.setEmail(newUser.getEmail());
        user.setPhone(newUser.getPhone());

    //  tổng tiền cần thanh toán
        double totalPrice = shopingCarts.stream()
                .mapToDouble(shopingCart -> shopingCart.getProduct().getPrice() * shopingCart.getQuantity())
                .sum();
    //  tạo mới order
        Orders order = orderService.add(user, totalPrice, false);
    //  tạo mới order detail
        for (ShopingCart shopingCart: shopingCarts) {
            int orderQuantity = shopingCart.getQuantity();
            Product product = shopingCart.getProduct();
            orderDetailService.add(product, order, orderQuantity);
        }
    //  Xóa tất cả sản phẩm khỏi giỏ hàng
        shopingCarts.forEach(shopingCart -> shopingCartService.delete(shopingCart.getId()));

        return "redirect:/user/account";
    }

    @PostMapping("/updateQuantity/{productId}")
    @ResponseBody
    public ShopingCart updateQuantity(@PathVariable("productId") Long productId, @RequestParam("quantity") int quantity) {
        Long userId = getUserId();
        ShopingCart shopingCart = shopingCartService.findByProductId(userId, productId);
        shopingCart.setQuantity(quantity);
        return shopingCartService.save(shopingCart);
    }
}
