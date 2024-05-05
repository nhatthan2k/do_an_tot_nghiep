package com.ra.Controller.user;

import com.ra.model.dto.request.WishListRequest;
import com.ra.model.entity.WishList;
import com.ra.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.ra.Controller.user.CartController.getUserId;

@Controller
@RequestMapping("/user/wish-list")
public class WishListController {
    @Autowired
    private WishListService wishListService;

    @GetMapping("/{id}")
    public String addWishList(@PathVariable("id") Long id) {
        Long userId = getUserId();
        WishList wishList = wishListService.findByUserandProduct(userId, id);
        if (wishList == null) {
            WishListRequest wishListRequest = new WishListRequest();
            wishListRequest.setProductId(id);
            wishListService.add(userId, wishListRequest);
        }
        return "redirect:/user/wish-list";
    }

    @GetMapping("")
    public String getAll(Model model) {
        Long userId = getUserId();
        List<WishList> wishLists = wishListService.getAll(userId);
        model.addAttribute("wishLists", wishLists);
        return "/shop/wishlist";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long wishListId) {
        Long userId = getUserId();
        wishListService.delete(wishListId, userId);
        return "redirect:/user/wish-list";
    }
}
