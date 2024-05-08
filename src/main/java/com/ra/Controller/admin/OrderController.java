package com.ra.Controller.admin;

import com.ra.model.entity.OrderDetail;
import com.ra.model.entity.Orders;
import com.ra.service.OrderDetailService;
import com.ra.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/orders")
    public String ordersPage(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "created", name = "sort") String sort,
            @RequestParam(defaultValue = "desc", name = "order") String order,
            Model model) {
        Pageable pageable;
        if (order.equals("asc")) {
            pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        }

        Page<Orders> orders = orderService.findAll(pageable);
        int currentPage = orders.getNumber();
        model.addAttribute("orders", orders);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPage", orders.getTotalPages());
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
