package com.ra.service;

import com.ra.model.dto.request.CategoryRequest;
import com.ra.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    Page<Category> getAll(Pageable pageable);
    List<Category> findAll();
    Category save(Category category);
    Category findById(Long id);
    void delete(Long id);
    List<Category> getbyStatus();
    List<Category> searchByName(String keyword);
}
