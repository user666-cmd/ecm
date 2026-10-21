package com.gra.ecm.dao;

import com.gra.ecm.model.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {
    List<Order> findByUserId(Integer userId);
    Order findById(Integer id);
    Order findByOrderNo(String orderNo);
    int insert(Order order);
    int update(Order order);
    int deleteById(Integer id);
    int updateStatus(@Param("id") Integer id, @Param("status") Integer status);
}