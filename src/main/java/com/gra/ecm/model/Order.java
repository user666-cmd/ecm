package com.gra.ecm.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class Order {
    private Integer id;
    private String orderNo;
    private Integer userId;
    private Integer addressId;
    private BigDecimal totalAmount;
    private Integer status;
    private String remark;
    
    private List<OrderItem> orderItems;
    private String receiverName;
    private String receiverPhone;
    private String fullAddress;
}