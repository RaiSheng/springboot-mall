package com.rswang.springbootmall.dao.impl;


import com.rswang.springbootmall.constant.ProductCategory;
import com.rswang.springbootmall.dao.ProductDao;
import com.rswang.springbootmall.dto.ProductRequest;
import com.rswang.springbootmall.model.Product;
import com.rswang.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Product> getProducts(ProductCategory category, String search) {
        String sql = "select product_id, product_name, category" +
                ", image_url, price, stock, description, created_date" +
                ", last_modified_date from product WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        if (category != null) {
            sql = sql + " and category=:category";
            map.put("category", category.name());
        }

        if (search != null) {
            sql = sql + " and product_name like :search";
            map.put("search", "%" + search + "%");
        }

        return namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
    }

    @Override
    public Product getProductById(Integer productId) {
        String sql = "select product_id, product_name, category" +
                ", image_url, price, stock, description, created_date" +
                ", last_modified_date from product where product_id = :productId";

        Map<String, Object> params = new HashMap<>();
        params.put("productId", productId);

        List<Product> productList = namedParameterJdbcTemplate.query(sql, params, new ProductRowMapper());

        if (productList != null && productList.size() > 0) {
            return productList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql ="INSERT product (product_name, category, image_url, price, stock" +
                ", description, created_date, last_modified_date) " +
                "VALUES (:productName, :category, :imageUrl, :price, :stock" +
                ", :description, :created_date, :last_modified_date)";

        Map<String, Object> params = new HashMap<>();
        params.put("productName", productRequest.getProductName());
        params.put("category", productRequest.getCategory().toString());
        params.put("imageUrl", productRequest.getImageUrl());
        params.put("price", productRequest.getPrice());
        params.put("stock", productRequest.getStock());
        params.put("description", productRequest.getDescription());

        Date now = new Date();
        params.put("created_date", now);
        params.put("last_modified_date", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params), keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        String sql = "UPDATE product SET product_name = :productName, category = :category" +
                ", image_url = :imageUrl, price = :price, stock = :stock" +
                ", description = :description, last_modified_date = :last_modified_date " +
                "WHERE product_id = :productId";

        Map<String, Object> params = new HashMap<>();
        params.put("productId", productId);
        params.put("productName", productRequest.getProductName());
        params.put("category", productRequest.getCategory().toString());
        params.put("imageUrl", productRequest.getImageUrl());
        params.put("price", productRequest.getPrice());
        params.put("stock", productRequest.getStock());
        params.put("description", productRequest.getDescription());
        params.put("last_modified_date", new Date());

        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void deleteProductById(Integer productId) {
        String sql = "DELETE FROM product WHERE product_id = :productId";

        Map<String, Object> params = new HashMap<>();
        params.put("productId", productId);

        namedParameterJdbcTemplate.update(sql, params);
    }
}
