package com.mahfuz.ecommerce_backend.product.service.impl;

import com.mahfuz.ecommerce_backend.common.exception.ResourceConflictException;
import com.mahfuz.ecommerce_backend.common.exception.ResourceNotFoundException;
import com.mahfuz.ecommerce_backend.product.dto.ProductCreateRequest;
import com.mahfuz.ecommerce_backend.product.dto.ProductUpdateRequest;
import com.mahfuz.ecommerce_backend.product.entity.Category;
import com.mahfuz.ecommerce_backend.product.entity.Inventory;
import com.mahfuz.ecommerce_backend.product.entity.Product;
import com.mahfuz.ecommerce_backend.product.mapper.ProductMapper;
import com.mahfuz.ecommerce_backend.product.repository.CategoryRepository;
import com.mahfuz.ecommerce_backend.product.repository.InventoryRepository;
import com.mahfuz.ecommerce_backend.product.repository.ProductRepository;
import com.mahfuz.ecommerce_backend.product.service.ProductService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final InventoryRepository inventoryRepository;
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
        Product savedProduct = productRepository.save(product);
        Inventory inventory = Inventory.builder()
                .product(savedProduct)
                .quantity(0)
                .build();
        inventoryRepository.save(inventory);
        return savedProduct;
    }

    public Product getById(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + id + " not found."));
    }

    public Page<Product> getAll(Pageable pageable){
        return productRepository.findAll(pageable);
    }

    public Product update(Long id, ProductUpdateRequest request){
        Product product = getById(id);
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        return productRepository.save(product);
    }

    public Product updateCategory(Long id, String categoryCode){
        Product product = getById(id);
        Category category = categoryRepository.findByCode(categoryCode)
                .orElseThrow(() -> new ResourceNotFoundException("Category with code " + categoryCode + " not found."));
        product.setCategory(category);
        return productRepository.save(product);
    }

    public Product toggleActiveStatus(Long id, boolean isActive){
        Product product = getById(id);
        product.setActive(isActive);
        return productRepository.save(product);
    }

    public void delete(Long id){
        if(!productRepository.existsById(id)){
            throw new ResourceNotFoundException("Product with ID " + id + " not found.");
        }
        productRepository.deleteById(id);
    }
}
