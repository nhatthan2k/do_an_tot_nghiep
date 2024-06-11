package com.ra.Controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.ra.config.PaypalPaymentIntent;
import com.ra.config.PaypalPaymentMethod;
import com.ra.model.entity.Orders;
import com.ra.model.entity.Product;
import com.ra.model.entity.ShopingCart;
import com.ra.model.entity.Users;
import com.ra.service.OrderDetailService;
import com.ra.service.OrderService;
import com.ra.service.ShopingCartService;
import com.ra.service.UserService;
import com.ra.service.impl.PaypalService;
import com.ra.util.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.ra.Controller.user.CartController.getUserId;

@Controller
public class PaymentController {
    @Autowired
    private ShopingCartService shopingCartService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;
    public static final String URL_PAYPAL_SUCCESS = "pay/success";
    public static final String URL_PAYPAL_CANCEL = "pay/cancel";

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private PaypalService paypalService;

    @PostMapping("/pay")
    public String pay(HttpServletRequest request, @RequestParam("price") double price) {
        String cancelUrl = Utils.getBaseURL(request) + "/" + URL_PAYPAL_CANCEL;
        String successUrl = Utils.getBaseURL(request) + "/" + URL_PAYPAL_SUCCESS;
        try {
            Payment payment = paypalService.createPayment(
                    price,
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    "payment description",
                    cancelUrl,
                    successUrl);
            for (Links links : payment.getLinks())
                if (links.getRel().equals("approval_url"))
                    return "redirect:" + links.getHref();


        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping("/pay/successfully")
    public String handleSuccessfulPayment() {
        Long userId = getUserId();
        //  lấy ra giỏ hàng của tài khoản đang đăng nhập
        List<ShopingCart> shopingCarts = shopingCartService.getAll(userId);
        //  lấy ra thông tin tài khoản đang đăng nhập
        Users user = userService.findById(userId);

        //  tổng tiền cần thanh toán
        double totalPrice = shopingCarts.stream()
                .mapToDouble(shopingCart -> shopingCart.getProduct().getPrice() * shopingCart.getQuantity())
                .sum();
        //  tạo mới order
        Orders order = orderService.add(user, totalPrice, true);
        //  tạo mới order detail
        for (ShopingCart shopingCart : shopingCarts) {
            int orderQuantity = shopingCart.getQuantity();
            Product product = shopingCart.getProduct();
            orderDetailService.add(product, order, orderQuantity);
        }
        //  Xóa tất cả sản phẩm khỏi giỏ hàng
        shopingCarts.forEach(shopingCart -> shopingCartService.delete(shopingCart.getId()));

        return "redirect:/user/account";
    }

    @GetMapping(URL_PAYPAL_CANCEL)
    public String cancelPay() {
        return "redirect:/user/checkout";
    }

    @GetMapping(URL_PAYPAL_SUCCESS)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved"))
                return "redirect:/pay/successfully";
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
    }
}
