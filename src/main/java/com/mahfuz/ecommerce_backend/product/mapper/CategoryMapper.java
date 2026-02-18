package com.mahfuz.ecommerce_backend.product.mapper;

import com.mahfuz.ecommerce_backend.product.dto.CategoryCreateRequest;
import com.mahfuz.ecommerce_backend.product.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
    Category toEntity(CategoryCreateRequest request);
}
