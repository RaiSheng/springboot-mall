package com.rswang.springbootmall.dao.impl;


import com.rswang.springbootmall.dao.ProductDao;
import com.rswang.springbootmall.model.Product;
import com.rswang.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(Integer productId) {
        String sql = "select product_id, product_name, category" +
                ", image_url, price, stock, description, created_date" +
                ", last_modified_date from product where product_id = :productId";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("productId", productId);

        List<Product> productList = namedParameterJdbcTemplate.query(sql, params, new ProductRowMapper());

        if (productList != null && productList.size() > 0) {
            return productList.get(0);
        } else {
            return null;
        }
    }
}
