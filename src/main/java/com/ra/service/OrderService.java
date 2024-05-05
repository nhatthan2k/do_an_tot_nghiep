package com.ra.service;

import com.ra.model.entity.EOrderStatus;
import com.ra.model.entity.Orders;
import com.ra.model.entity.Users;

import java.util.List;

public interface OrderService {
    List<Orders> findAll();
    List<Orders> getAll(Long userId);
    Orders add(Users users, Double totalPrice);
    Orders getbySerial(Long userId, String serial);
    List<Orders> getByStatus(Long userId, EOrderStatus status);
    Orders save(Orders orders);
    Orders findById(Long id);
    Orders getByIdAndStatus(Long userId, Long orderId, EOrderStatus status);
}
