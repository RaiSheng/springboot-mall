package com.rswang.springbootmall.service;

import com.rswang.springbootmall.dto.CreateOrderRequest;
import com.rswang.springbootmall.dto.OrderQueryParams;
import com.rswang.springbootmall.model.Order;

import java.util.List;

public interface OrderService {

    Integer countOrder(OrderQueryParams orderQueryParams);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Order getOrderById(Integer orderId);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
