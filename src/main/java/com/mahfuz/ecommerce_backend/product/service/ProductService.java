package com.mahfuz.ecommerce_backend.product.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mahfuz.ecommerce_backend.product.dto.ProductCreateRequest;
import com.mahfuz.ecommerce_backend.product.dto.ProductUpdateRequest;
import com.mahfuz.ecommerce_backend.product.entity.Product;

public interface ProductService {
    Product create(ProductCreateRequest request);

    Product getById(Long id);

    Page<Product> getAll(Pageable pageable);

    Product update(Long id, ProductUpdateRequest request);

    Product updateCategory(Long id, String categoryCode);

    Product toggleActiveStatus(Long id, boolean isActive);

    void delete(Long id);
}
