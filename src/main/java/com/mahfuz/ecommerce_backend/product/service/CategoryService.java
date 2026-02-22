package com.mahfuz.ecommerce_backend.product.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mahfuz.ecommerce_backend.product.dto.CategoryCreateRequest;
import com.mahfuz.ecommerce_backend.product.dto.CategoryUpdateRequest;
import com.mahfuz.ecommerce_backend.product.entity.Category;

public interface CategoryService {
    Category create(CategoryCreateRequest request);

    Category getById(Long id);

    Page<Category> getAll(Pageable pageable);

    Category update(Long id, CategoryUpdateRequest request);

    Category toggleActiveStatus(Long id, boolean isActive);

    void delete(Long id);
}
