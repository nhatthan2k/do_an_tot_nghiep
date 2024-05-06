package com.ra.Controller.admin;

import com.ra.model.entity.Users;
import com.ra.service.UserService;
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
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public String userPage(Model model,
        @RequestParam(defaultValue = "12", name = "limit") int limit,
        @RequestParam(defaultValue = "0", name = "page") int page,
        @RequestParam(defaultValue = "id", name = "sort") String sort,
        @RequestParam(defaultValue = "asc", name = "order") String order,
        @RequestParam(value = "nameSearch",required = false) String nameSearch
    ) {
        Pageable pageable;
        if (order.equals("asc")) {
            pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        }else {
            pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        }

        if (nameSearch != null && nameSearch.trim().isEmpty()) {
            nameSearch = null;
        }

        Page<Users> users = userService.getAll(pageable, nameSearch);
        int currentPage = users.getNumber();
        model.addAttribute("users", users);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPage", users.getTotalPages());
        model.addAttribute("nameSearch", nameSearch);
        return "/admin/user/user";
    }

    @GetMapping("/user/status/{id}")
    public String updateStatus(@PathVariable("id") Long id) {
        Users user = userService.findById(id);
        user.setStatus(!user.isStatus());
        userService.save(user);
        return "redirect:/admin/user";
    }
}
