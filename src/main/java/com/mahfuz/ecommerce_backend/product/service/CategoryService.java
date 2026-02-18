package com.mahfuz.ecommerce_backend.product.service;

import com.mahfuz.ecommerce_backend.product.dto.CategoryCreateRequest;
import com.mahfuz.ecommerce_backend.product.entity.Category;

public interface CategoryService {
    Category create(CategoryCreateRequest request);
}
