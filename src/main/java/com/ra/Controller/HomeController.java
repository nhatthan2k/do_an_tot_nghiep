package com.ra.Controller;

import com.ra.model.dto.request.ShopingCartRequest;
import com.ra.model.entity.Category;
import com.ra.model.entity.Product;
import com.ra.service.CategoryService;
import com.ra.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String Home(HttpSession session, Model model,
        @RequestParam(defaultValue = "12", name = "limit") int limit,
        @RequestParam(defaultValue = "0", name = "page") int page,
        @RequestParam(defaultValue = "id", name = "sort") String sort,
        @RequestParam(defaultValue = "asc", name = "order") String order
    ) {
        Pageable pageable;
        if (order.equals("asc")) {
            pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        }
        Page<Product> products = productService.getByCategoryStatus(pageable, true);

        model.addAttribute("products", products);
        List<Category> categories = categoryService.getbyStatus();
        session.setAttribute("categories", categories);
        return "/index";
    }

    @GetMapping("/search")
    public String searchHome(Model model,
        @RequestParam(defaultValue = "5", name = "limit") int limit,
        @RequestParam(defaultValue = "0", name = "page") int page,
        @RequestParam(defaultValue = "id", name = "sort") String sort,
        @RequestParam(defaultValue = "asc", name = "order") String order,
        @RequestParam("keyword") String keyWord
    ) {
        Pageable pageable;
        if (order.equals("asc")) {
            pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        }
        Page<Product> products = productService.searchByName(keyWord, pageable);
        int currentPage = products.getNumber();
        model.addAttribute("products", products);
        model.addAttribute("searchName", keyWord);
        model.addAttribute("totalProducts", products.getTotalElements());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPage", products.getTotalPages());
        model.addAttribute("limit", limit);
        model.addAttribute("sort", sort);
        model.addAttribute("order", order);
        return "/shop/shop-4-column";
    }

    @GetMapping("/categories/{id}")
    public String CategoryShop(Model model, @PathVariable("id") Long id,
        @RequestParam(defaultValue = "5", name = "limit") int limit,
        @RequestParam(defaultValue = "0", name = "page") int page,
        @RequestParam(defaultValue = "id", name = "sort") String sort,
        @RequestParam(defaultValue = "asc", name = "order") String order
    ) {
        Pageable pageable;
        if (order.equals("asc")) {
            pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        }

        Page<Product> products = productService.getByCategoryId(id, pageable);
        int currentPage = products.getNumber();
        model.addAttribute("products", products);
        model.addAttribute("totalProducts", products.getTotalElements());
        model.addAttribute("categoryId", id);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPage", products.getTotalPages());
        model.addAttribute("limit", limit);
        model.addAttribute("sort", sort);
        model.addAttribute("order", order);
        return "/shop/shop-4-column";
    }

    @GetMapping("/products/{id}")
    public String singleProduct(Model model, @PathVariable("id") Long id) {
        Product product = productService.findById(id);
        List<Product> products = productService.getByCategoryId(product.getCategory().getId());
        products.remove(product);
        model.addAttribute("product", product);
        model.addAttribute("products", products);
        return "/shop/single-product";
    }

    @GetMapping("/blog")
    public String blogPage() {
        return "/shop/blog";
    }

    @GetMapping("/contact")
    public String contactPage() {
        return "/shop/contact";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "/shop/about";
    }
}
