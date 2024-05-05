package com.ra.service;

import com.ra.model.dto.request.WishListRequest;
import com.ra.model.entity.WishList;

import java.util.List;

public interface WishListService {
    WishList add(Long userId, WishListRequest wishListRequest);
    List<WishList> getAll(Long userId);
    void delete(Long wishListId, Long userId);
    WishList findByUserandProduct(Long userId, Long productId);
}
