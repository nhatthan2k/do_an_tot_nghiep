package com.ra.Controller.user;

import com.ra.model.dto.request.PasswordRequest;
import com.ra.model.entity.Orders;
import com.ra.model.entity.Users;
import com.ra.service.OrderService;
import com.ra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.ra.Controller.user.CartController.getUserId;

@Controller
@RequestMapping("/user/account")
public class accountController {
    @Value("${path-upload}")
    private String pathUpload;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OrderService orderService;

    @GetMapping("")
    public String getAccount(Model model) {
        Long userId = getUserId();
        Users user = userService.findById(userId);
        PasswordRequest passwordRequest = new PasswordRequest();
        List<Orders> orders = orderService.getAll(userId);
        model.addAttribute("user", user);
        model.addAttribute("passwordRequest", passwordRequest);
        model.addAttribute("orders", orders);
        return "/shop/my-account";
    }

    @PostMapping("/edit-account")
    public String updateAcc(@ModelAttribute("user") Users user, @RequestParam("imageUser") MultipartFile file) {
        Long id = getUserId();
        String fileName = file.getOriginalFilename();
        try {
            FileCopyUtils.copy(file.getBytes(),new File(pathUpload+fileName));
            // lưu tên file vào database
            user.setAvatar(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        userService.updateAcc(user, id);
        return "redirect:/user/account";
    }


    @PostMapping("/change-password")
    public String changePassword(@ModelAttribute("passwordRequest") PasswordRequest passwordRequest) {
        Long id = getUserId();
        Users user = userService.findById(id);
        String oldPassword = user.getPassword();

        boolean isPaswordMatch = passwordEncoder.matches(passwordRequest.getOldPass(), oldPassword);
        if (isPaswordMatch) {
            if (!passwordRequest.getNewPass().equals(passwordRequest.getConfirmNewPass())) {
//                return new ResponseEntity<>("Xác nhận mật khẩu không chính xác!", HttpStatus.BAD_REQUEST);
            }
            user.setPassword(passwordEncoder.encode(passwordRequest.getNewPass()));
            userService.save(user);
//            return new ResponseEntity<>("cập nhật mật khẩu thành công", HttpStatus.OK);
        }

        return "redirect:/user/account";
    }
}
