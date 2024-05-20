package com.ra.service;

import com.ra.model.dto.request.ProductRequest;
import com.ra.model.entity.Category;
import com.ra.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Page<Product> getAll(Pageable pageable, String nameSearch);
    Product save(Product product);
    Product save(ProductRequest productRequest);
    Product findById(Long id);
    void delete(Long id);
    Page<Product> searchByName(String name, Pageable pageable);
    Page<Product> getByCategoryStatus(Pageable pageable, Boolean status);
    List<Product> getByCategoryId(Long id);
    Page<Product> getByCategoryId(Long id, Pageable pageable);
}
