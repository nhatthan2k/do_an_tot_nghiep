package com.ra.service;

import com.ra.model.entity.EOrderStatus;
import com.ra.model.entity.Orders;
import com.ra.model.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    Page<Orders> findAll(Pageable pageable);
    List<Orders> getAll(Long userId);
    Orders add(Users users, Double totalPrice, boolean payStatus);
    Orders getbySerial(Long userId, String serial);
    List<Orders> getByStatus(Long userId, EOrderStatus status);
    Orders save(Orders orders);
    Orders findById(Long id);
    Orders getByIdAndStatus(Long userId, Long orderId, EOrderStatus status);
}
