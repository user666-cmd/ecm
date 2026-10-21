package com.gra.ecm.dao;

import com.gra.ecm.model.OrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    List<OrderItem> findByOrderId(Integer orderId);
    int insert(OrderItem orderItem);
    int insertBatch(List<OrderItem> orderItems);
    int deleteByOrderId(Integer orderId);
}