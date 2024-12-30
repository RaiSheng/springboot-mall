package com.rswang.springbootmall.dao;

import com.rswang.springbootmall.dto.ProductRequest;
import com.rswang.springbootmall.model.Product;

public interface ProductDao {

    Product getProductById(Integer id);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);
}
