package com.gra.ecm.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItem {
    private Integer id;
    private Integer orderId;
    private Integer productId;
    private String productName;
    private BigDecimal productPrice;
    private Integer quantity;
    private BigDecimal subtotal;
    
    private String productImage;
}