package com.ra.Controller.admin;

import com.ra.model.entity.OrderDetail;
import com.ra.model.entity.Orders;
import com.ra.service.OrderDetailService;
import com.ra.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/orders")
    public String ordersPage(Model model) {
        List<Orders> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        return "/admin/orders/orders";
    }



    @GetMapping("/orders/details/{id}")
    public String ordersDetailsPage(@PathVariable Long id, Model model) {
        List<OrderDetail> orderDetails = orderDetailService.getByOrderId(id);
        model.addAttribute("orderDetails", orderDetails);
        Orders order = orderService.findById(id);
        model.addAttribute("order", order);
        return "/admin/orders/ordersDetails";
    }
}
