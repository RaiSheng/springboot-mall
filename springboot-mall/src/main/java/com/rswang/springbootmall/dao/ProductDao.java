package com.rswang.springbootmall.dao;

import com.rswang.springbootmall.dto.ProductRequest;
import com.rswang.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getProducts();

    Product getProductById(Integer id);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer id);
}
