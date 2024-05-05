package com.ra.repository;

import com.ra.model.entity.ShopingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ShopingCartRepository extends JpaRepository<ShopingCart, Integer>{
    @Query("SELECT s from ShopingCart s where s.users.id = :id")
    List<ShopingCart> findByUsers(@Param("id") Long id);

    @Query("SELECT s from ShopingCart s where s.product.id = :productId and s.users.id = :userId")
    ShopingCart findByUserandProduct(@Param("userId") Long userId, @Param("productId") Long productId);
}
