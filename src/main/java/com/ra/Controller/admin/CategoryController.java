package com.ra.Controller.admin;

import com.ra.model.dto.request.CategoryRequest;
import com.ra.model.entity.Category;
import com.ra.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category")
    public String categoryPage(Model model,
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

        Page<Category> categories = categoryService.getAll(pageable, nameSearch);
        int currentPage = categories.getNumber();
        model.addAttribute("categories", categories);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPage", categories.getTotalPages());
        model.addAttribute("nameSearch", nameSearch);
        return "/admin/category/category";
    }

//  add Category
    @GetMapping("/category/add-category")
    public String add(Model model) {
        CategoryRequest category = new CategoryRequest();
        category.setStatus(true);
        model.addAttribute("category", category);
        return "/admin/category/add-category";
    }

    @PostMapping("/category/add-category")
    public String save(@Valid @ModelAttribute("category") CategoryRequest category, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "/admin/category/add-category";
        }

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

    @GetMapping("/category/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        categoryService.delete(id);
        return "redirect:/admin/category";
    }
}
