package com.mahfuz.ecommerce_backend.product.service.impl;

import com.mahfuz.ecommerce_backend.common.exception.ResourceConflictException;
import com.mahfuz.ecommerce_backend.common.exception.ResourceNotFoundException;
import com.mahfuz.ecommerce_backend.product.dto.CategoryCreateRequest;
import com.mahfuz.ecommerce_backend.product.dto.CategoryUpdateRequest;
import com.mahfuz.ecommerce_backend.product.entity.Category;
import com.mahfuz.ecommerce_backend.product.mapper.CategoryMapper;
import com.mahfuz.ecommerce_backend.product.repository.CategoryRepository;
import com.mahfuz.ecommerce_backend.product.service.CategoryService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Category getById(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found."));
    }

    public Page<Category> getAll(Pageable pageable){
        return categoryRepository.findAll(pageable);
    }

    public Category update(Long id, CategoryUpdateRequest request){
        Category category = getById(id);
        category.setName(request.name());
        return categoryRepository.save(category);
    }

    public Category toggleActiveStatus(Long id, boolean isActive){
        Category category = getById(id);
        category.setActive(isActive);
        return categoryRepository.save(category);
    }

    public void delete(Long id){
        if(!categoryRepository.existsById(id)){
            throw new ResourceNotFoundException("Category with id " + id + " not found.");
        }
        categoryRepository.deleteById(id);
    }
}
