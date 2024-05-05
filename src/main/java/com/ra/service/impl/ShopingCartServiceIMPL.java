package com.ra.service.impl;

import com.ra.model.dto.request.ShopingCartRequest;
import com.ra.model.entity.Product;
import com.ra.model.entity.ShopingCart;
import com.ra.model.entity.Users;
import com.ra.repository.ShopingCartRepository;
import com.ra.service.ProductService;
import com.ra.service.ShopingCartService;
import com.ra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ShopingCartServiceIMPL implements ShopingCartService {
    @Autowired
    private ShopingCartRepository shopingCartRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @Override
    public List<ShopingCart> getAll(Long userId) {
        return shopingCartRepository.findByUsers(userId);
    }

    @Override
    public ShopingCart add(ShopingCartRequest shopingCartRequest, Long userId) {

        Product product = productService.findById(shopingCartRequest.getProductId());
        Users user = userService.findById(userId);

        ShopingCart shopingCart = ShopingCart.builder()
                .quantity(shopingCartRequest.getQuantity())
                .product(product)
                .users(user)
                .build();
        return shopingCartRepository.save(shopingCart);
    }

    @Override
    public ShopingCart findById(int id) {
        return shopingCartRepository.findById(id).orElse(null);
    }

    @Override
    public ShopingCart save(ShopingCart shopingCart) {
        return shopingCartRepository.save(shopingCart);
    }

    @Override
    public void delete(int id) {
        shopingCartRepository.deleteById(id);
    }

    @Override
    public ShopingCart findByProductId(Long userId, Long productId) {
        return shopingCartRepository.findByUserandProduct(userId, productId);
    }
}
