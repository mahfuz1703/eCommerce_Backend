package com.mahfuz.ecommerce_backend.product.service;

import com.mahfuz.ecommerce_backend.product.dto.ProductCreateRequest;
import com.mahfuz.ecommerce_backend.product.entity.Product;

public interface ProductService {
    Product create(ProductCreateRequest request);

    Product getById(Long id);
}
