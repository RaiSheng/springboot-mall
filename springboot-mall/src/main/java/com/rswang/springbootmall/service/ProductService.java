package com.rswang.springbootmall.service;

import com.rswang.springbootmall.dto.ProductRequest;
import com.rswang.springbootmall.model.Product;

public interface ProductService {

    Product getProductById(Integer id);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);
}
