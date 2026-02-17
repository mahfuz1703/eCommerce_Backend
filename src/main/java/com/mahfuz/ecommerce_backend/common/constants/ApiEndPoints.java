package com.mahfuz.ecommerce_backend.common.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ApiEndPoints {
    private static  final String API_VERSION = "/api/v1";
    private static  final String BASR_ADMIN = "/admin";

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CategoryAdmin{
        public static final String BASE_CATEGORY_ADMIN = API_VERSION + BASR_ADMIN + "/categories";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ProductAdmin{
        public static final String BASE_PRODUCT_ADMIN = API_VERSION + BASR_ADMIN + "/products";
    }
}
