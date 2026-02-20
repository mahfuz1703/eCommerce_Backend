package com.mahfuz.ecommerce_backend.product.service.impl;

import com.mahfuz.ecommerce_backend.common.exception.ResourceConflictException;
import com.mahfuz.ecommerce_backend.common.exception.ResourceNotFoundException;
import com.mahfuz.ecommerce_backend.product.dto.ProductCreateRequest;
import com.mahfuz.ecommerce_backend.product.entity.Category;
import com.mahfuz.ecommerce_backend.product.entity.Product;
import com.mahfuz.ecommerce_backend.product.mapper.ProductMapper;
import com.mahfuz.ecommerce_backend.product.repository.CategoryRepository;
import com.mahfuz.ecommerce_backend.product.repository.ProductRepository;
import com.mahfuz.ecommerce_backend.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public Product create(ProductCreateRequest request){
        if(productRepository.existsBySku(request.sku())){
            throw new ResourceConflictException("Product with SKU " + request.sku() + " already exists.");
        }

        Optional<Category> categoryOptional = categoryRepository.findByCode(request.categoryCode());
        if (categoryOptional.isEmpty()) {
            throw new ResourceNotFoundException("Category with code " + request.categoryCode() + " not found.");
        }
        Product product = productMapper.toEntity(request, categoryOptional.get());
        return productRepository.save(product);
    }

    public Product getById(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + id + " not found."));
    }
}
