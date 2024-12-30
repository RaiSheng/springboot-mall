package com.rswang.springbootmall.dao;

import com.rswang.springbootmall.model.Product;

public interface ProductDao {

    Product getProductById(Integer id);
}
