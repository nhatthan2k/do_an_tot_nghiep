package com.ra.repository;

import com.ra.model.entity.Category;
import com.ra.model.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByStatus(boolean status);

    Page<Category> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
    @Query("SELECT c from Category c WHERE c.name like ?1% ")
    List<Category> searchCategoriesByName(String keyword);
}
