package com.rswang.springbootmall.dao.impl;

import com.rswang.springbootmall.dao.ProductDao;
import com.rswang.springbootmall.dto.ProductQueryParams;
import com.rswang.springbootmall.dto.ProductRequest;
import com.rswang.springbootmall.model.Product;
import com.rswang.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer countProduct(ProductQueryParams productQueryParams) {
        String sql = "select count(*) from product where 1=1";

        Map<String, Object> map = new HashMap<>();

        // 查詢條件
        sql = addFilterSql(sql, map, productQueryParams);

        return namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
    }

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        String sql = "select product_id, product_name, category" +
                ", image_url, price, stock, description, created_date" +
                ", last_modified_date from product WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        // 查詢條件
        sql = addFilterSql(sql, map, productQueryParams);

        // 排序
        sql = sql + " ORDER BY " + productQueryParams.getOrderBy() + " " + productQueryParams.getSort();

        // 分頁
        sql= sql + " LIMIT :limit OFFSET :offset";
        map.put("limit", productQueryParams.getLimit());
        map.put("offset", productQueryParams.getOffset());

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

        if (!productList.isEmpty()) {
            return productList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql ="INSERT INTO product (product_name, category, image_url, price, stock" +
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

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
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
    public void updateStock(Integer productId, Integer stock) {
        String sql = "UPDATE product " +
                "SET stock = :stock, last_modified_date = :lastModifiedDate " +
                "WHERE product_id = :productId";

        Map<String, Object> params = new HashMap<>();
        params.put("stock", stock);
        params.put("lastModifiedDate", new Date());
        params.put("productId", productId);

        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void deleteProductById(Integer productId) {
        String sql = "DELETE FROM product WHERE product_id = :productId";

        Map<String, Object> params = new HashMap<>();
        params.put("productId", productId);

        namedParameterJdbcTemplate.update(sql, params);
    }

    private String addFilterSql(String sql, Map<String, Object> params, ProductQueryParams productQueryParams) {
        // 查詢條件
        if (productQueryParams.getCategory() != null) {
            sql = sql + " and category=:category";
            params.put("category", productQueryParams.getCategory().name());
        }

        if (productQueryParams.getSearch() != null) {
            sql = sql + " and product_name like :search";
            params.put("search", "%" + productQueryParams.getSearch() + "%");
        }

        return sql;
    }
}
