package com.ra.service.impl;

import com.ra.model.entity.EOrderStatus;
import com.ra.model.entity.Orders;
import com.ra.model.entity.Users;
import com.ra.repository.OrderRepository;
import com.ra.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceIMPL implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Orders> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Orders> getAll(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }

    @Override
    public Orders add(Users users, Double totalPrice) {
        Orders order = Orders.builder()
                .orderNumber(UUID.randomUUID().toString())
                .users(users)
                .price(totalPrice)
                .status(EOrderStatus.WAITING)
                .receiveName(users.getFullName())
                .receiveAddress(users.getAddress())
                .receivePhone(users.getPhone())
                .created(new java.sql.Date(new java.util.Date().getTime()))
                .build();
        return orderRepository.save(order);
    }

    @Override
    public Orders getbySerial(Long userId, String serial) {
        return orderRepository.findByUserIdAndSerial(userId, serial);
    }

    @Override
    public List<Orders> getByStatus(Long userId, EOrderStatus status) {
        return orderRepository.findByUserIdAndStatus(userId, status);
    }

    @Override
    public Orders save(Orders orders) {
        return orderRepository.save(orders);
    }

    @Override
    public Orders findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Orders getByIdAndStatus(Long userId, Long orderId, EOrderStatus status) {
        return orderRepository.findByIdAndStatus(userId, orderId, status);
    }
}
