package com.ra.Controller.admin;

import com.ra.model.dto.request.ProductRequest;
import com.ra.model.entity.Category;
import com.ra.model.entity.Product;
import com.ra.service.CategoryService;
import com.ra.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class ProductController {
    @Value("${path-upload}")
    private String pathUpload;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/product")
    public String productPage(Model model,
        @RequestParam(defaultValue = "5", name = "limit") int limit,
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

        Page<Product> products = productService.getAll(pageable, nameSearch);
        int currentPage = products.getNumber();
        model.addAttribute("products", products);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPage", products.getTotalPages());
        model.addAttribute("nameSearch", nameSearch);
        return "/admin/product/product";
    }

    //  add product
    @GetMapping("/product/add-product")
    public String add(Model model) {
        List<Category> categories = categoryService.getbyStatus();
        model.addAttribute("categories", categories);
        ProductRequest product = new ProductRequest();
        model.addAttribute("product", product);
        return "/admin/product/add-product";
    }

    @PostMapping("/product/add-product")
    public String save(@Valid @ModelAttribute("product") ProductRequest product, BindingResult bindingResult, @RequestParam("imageProduct") MultipartFile file, Model model) {
        if (bindingResult.hasErrors()) {
            List<Category> categories = categoryService.getbyStatus();
            model.addAttribute("categories", categories);
            return "/admin/product/add-product";
        }

        String fileName = file.getOriginalFilename();
        try {
            FileCopyUtils.copy(file.getBytes(),new File(pathUpload+fileName));
            // lưu tên file vào database
            product.setImage(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        productService.save(product);
        return "redirect:/admin/product";
    }

    //  edit Category
    @GetMapping("/product/edit-product/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {
        List<Category> categories = categoryService.getbyStatus();
        model.addAttribute("categories", categories);
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "/admin/product/edit-product";
    }

    @PostMapping("/product/edit-product")
    public String update(@ModelAttribute("product") Product product, @RequestParam("imageProduct") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try {
            FileCopyUtils.copy(file.getBytes(),new File(pathUpload+fileName));
            // lưu tên file vào database
            product.setImage(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        productService.save(product);
        return "redirect:/admin/product";
    }

    @GetMapping("/product/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        productService.delete(id);
        return "redirect:/admin/product";
    }
}
