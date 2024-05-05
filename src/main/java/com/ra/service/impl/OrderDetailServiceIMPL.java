package com.ra.service.impl;

import com.ra.model.entity.OrderDetail;
import com.ra.model.entity.Orders;
import com.ra.model.entity.Product;
import com.ra.repository.OrderDetailRepository;
import com.ra.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceIMPL implements OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Override
    public List<OrderDetail> getByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

    @Override
    public OrderDetail add(Product product, Orders orders, int orderQuatity) {
        OrderDetail orderDetail = OrderDetail.builder()
                .orders(orders)
                .product(product)
                .name(product.getName())
                .price(product.getPrice())
                .quantity(orderQuatity)
                .build();
        return orderDetailRepository.save(orderDetail);
    }
}
