package com.mahfuz.ecommerce_backend.product.service.impl;

import com.mahfuz.ecommerce_backend.common.exception.ResourceConflictException;
import com.mahfuz.ecommerce_backend.product.dto.CategoryCreateRequest;
import com.mahfuz.ecommerce_backend.product.entity.Category;
import com.mahfuz.ecommerce_backend.product.mapper.CategoryMapper;
import com.mahfuz.ecommerce_backend.product.repository.CategoryRepository;
import com.mahfuz.ecommerce_backend.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public Category create(CategoryCreateRequest request) {
        if (categoryRepository.existsByCode(request.code())) {
            throw new ResourceConflictException("Category with code " + request.code() + " already exists.");
        }
        Category category = categoryMapper.toEntity(request);
        return categoryRepository.save(category);
    }
}
