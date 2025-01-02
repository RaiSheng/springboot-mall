package com.rswang.springbootmall.service;

import com.rswang.springbootmall.dto.CreateOrderRequest;
import com.rswang.springbootmall.model.Order;

public interface OrderService {

    Order getOrderById(Integer orderId);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
