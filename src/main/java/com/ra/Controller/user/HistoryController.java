package com.ra.Controller.user;

import com.ra.model.entity.EOrderStatus;
import com.ra.model.entity.Orders;
import com.ra.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HistoryController {
    @Autowired
    private OrderService orderService;
    @GetMapping("/user/orders/cancel/{id}")
    public String confirmOrder(@PathVariable Long id) {
        Orders order = orderService.findById(id);
        if (order != null && order.getStatus() == EOrderStatus.WAITING) {
            order.setStatus(EOrderStatus.CANCEL);
            orderService.save(order);
        }
        return "redirect:/user/account";
    }
}
