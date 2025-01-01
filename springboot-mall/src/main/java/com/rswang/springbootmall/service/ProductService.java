package com.rswang.springbootmall.service;

import com.rswang.springbootmall.dto.ProductQueryParams;
import com.rswang.springbootmall.dto.ProductRequest;
import com.rswang.springbootmall.model.Product;

import java.util.List;

public interface ProductService {

    Integer countProduct(ProductQueryParams productQueryParams);

    List<Product> getProducts(ProductQueryParams productQueryParams);

    Product getProductById(Integer id);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
