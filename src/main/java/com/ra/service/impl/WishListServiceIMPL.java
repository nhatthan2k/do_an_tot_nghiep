package com.ra.service.impl;

import com.ra.model.dto.request.WishListRequest;
import com.ra.model.entity.Product;
import com.ra.model.entity.Users;
import com.ra.model.entity.WishList;
import com.ra.repository.WishListRepository;
import com.ra.service.ProductService;
import com.ra.service.UserService;
import com.ra.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListServiceIMPL implements WishListService {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private WishListRepository wishListRepository;

    @Override
    public WishList add(Long userId, WishListRequest wishListRequest) {
        Users user = userService.findById(userId);

        Product product = productService.findById(wishListRequest.getProductId());

        if (product == null) {
            throw new RuntimeException("không tồn tại sản phẩm");
        }

        WishList wishList = WishList.builder()
                .users(user)
                .product(product)
                .build();
        return wishListRepository.save(wishList);
    }

    @Override
    public List<WishList> getAll(Long userId) {
        return wishListRepository.getAllByUserId(userId);
    }

    @Override
    public void delete(Long wishListId, Long userId) {
        wishListRepository.deleteByIdAndUserId(wishListId, userId);
    }

    @Override
    public WishList findByUserandProduct(Long userId, Long productId) {
        return wishListRepository.findByUserandProduct(userId, productId);
    }
}
