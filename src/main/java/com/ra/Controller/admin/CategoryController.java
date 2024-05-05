package com.ra.Controller.admin;

import com.ra.model.entity.Category;
import com.ra.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category")
    public String categoryPage(Model model,
        @RequestParam(defaultValue = "12", name = "limit") int limit,
        @RequestParam(defaultValue = "0", name = "page") int page,
        @RequestParam(defaultValue = "id", name = "sort") String sort,
        @RequestParam(defaultValue = "asc", name = "order") String order
    ) {
        Pageable pageable;
        if (order.equals("asc")) {
            pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        }else {
            pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        }

        Page<Category> categories = categoryService.getAll(pageable);
        model.addAttribute("categories", categories);
        return "/admin/category/category";
    }

//  add Category
    @GetMapping("/category/add-category")
    public String add(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "/admin/category/add-category";
    }

    @PostMapping("/category/add-category")
    public String save(@ModelAttribute("category") Category category) {
        categoryService.save(category);
        return "redirect:/admin/category";
    }

//    update status
    @GetMapping("/category/status/{id}")
    public String updateStatus(@PathVariable("id") Long id) {
        Category category = categoryService.findById(id);
        category.setStatus(!category.isStatus());
        categoryService.save(category);
        return "redirect:/admin/category";
    }

//  edit Category
    @GetMapping("/category/edit-category/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {
        Category category = categoryService.findById(id);
        model.addAttribute("category", category);
        return "/admin/category/edit-category";
    }

    @PostMapping("/category/edit-category")
    public String update(@ModelAttribute("category") Category category) {
        categoryService.save(category);
        return "redirect:/admin/category";
    }

    @GetMapping ("/category/search")
    public String searchByName(@RequestParam("nameSearch") String keyword, Model model) {
        List<Category> categories = categoryService.searchByName(keyword);
        model.addAttribute("categories", categories);
        return "/admin/category/category";
    }

    @GetMapping("/category/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        categoryService.delete(id);
        return "redirect:/admin/category";
    }
}
