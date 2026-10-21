package com.gra.ecm.service.impl;

import com.gra.ecm.dao.OrderItemMapper;
import com.gra.ecm.dao.OrderMapper;
import com.gra.ecm.dao.ProductMapper;
import com.gra.ecm.dao.UserBalanceMapper;
import com.gra.ecm.model.Order;
import com.gra.ecm.model.OrderItem;
import com.gra.ecm.model.UserBalance;
import com.gra.ecm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Order> findByUserId(Integer userId) {
        List<Order> orders = orderMapper.findByUserId(userId);
        for (Order order : orders) {
            List<OrderItem> items = orderItemMapper.findByOrderId(order.getId());
            order.setOrderItems(items);
        }
        return orders;
    }

    @Override
    public Order findById(Integer id) {
        Order order = orderMapper.findById(id);
        if (order != null) {
            List<OrderItem> items = orderItemMapper.findByOrderId(order.getId());
            order.setOrderItems(items);
        }
        return order;
    }

    @Autowired
    private UserBalanceMapper userBalanceMapper;

    @Override
    @Transactional
    public Order createOrder(Order order) {
        String orderNo = generateOrderNo();
        order.setOrderNo(orderNo);
        
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> items = order.getOrderItems();

        for (OrderItem item : items) {
            BigDecimal subtotal = item.getProductPrice().multiply(new BigDecimal(item.getQuantity()));
            item.setSubtotal(subtotal);
            totalAmount = totalAmount.add(subtotal);
        }

        order.setTotalAmount(totalAmount);
        
        UserBalance userBalance = userBalanceMapper.findByUserId(order.getUserId());
        if (userBalance == null || userBalance.getBalance().compareTo(totalAmount) < 0) {
            throw new RuntimeException("余额不足");
        }
        
        int frozen = userBalanceMapper.freezeAmount(order.getUserId(), totalAmount);
        if (frozen == 0) {
            throw new RuntimeException("余额冻结失败");
        }
        
        order.setStatus(1);
        orderMapper.insert(order);

        for (OrderItem item : items) {
            item.setOrderId(order.getId());
            orderItemMapper.insert(item);
            int updated = productMapper.updateStockAndSales(item.getProductId(), item.getQuantity());
            if (updated == 0) {
                throw new RuntimeException("库存不足，无法完成订单");
            }
        }
        
        return order;
    }

    @Override
    public boolean updateOrder(Order order) {
        return orderMapper.update(order) > 0;
    }

    @Override
    public boolean updateOrderStatus(Integer id, Integer status) {
        return orderMapper.updateStatus(id, status) > 0;
    }

    @Override
    @Transactional
    public boolean cancelOrder(Integer id) {
        Order order = orderMapper.findById(id);
        if (order == null || order.getStatus() != 1) {
            return false;
        }
        
        List<OrderItem> items = orderItemMapper.findByOrderId(id);
        
        for (OrderItem item : items) {
            productMapper.updateStockAndSalesReverse(item.getProductId(), item.getQuantity());
        }
        
        BigDecimal totalAmount = order.getTotalAmount();
        userBalanceMapper.unfreezeAmount(order.getUserId(), totalAmount);
        
        order.setStatus(2);
        return orderMapper.update(order) > 0;
    }

    @Override
    @Transactional
    public boolean deleteOrder(Integer id) {
        orderItemMapper.deleteByOrderId(id);
        return orderMapper.deleteById(id) > 0;
    }

    private String generateOrderNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = sdf.format(new Date());
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
        return timestamp + uuid;
    }
}