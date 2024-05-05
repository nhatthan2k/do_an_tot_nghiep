package com.ra.service;

import com.ra.model.entity.OrderDetail;
import com.ra.model.entity.Orders;
import com.ra.model.entity.Product;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetail> getByOrderId(Long orderId);
    OrderDetail add(Product product, Orders orders, int orderQuantity);
}
