package com.gra.ecm.service;

import com.gra.ecm.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> findByUserId(Integer userId);
    Order findById(Integer id);
    Order createOrder(Order order);
    boolean updateOrder(Order order);
    boolean updateOrderStatus(Integer id, Integer status);
    boolean cancelOrder(Integer id);
    boolean deleteOrder(Integer id);
}