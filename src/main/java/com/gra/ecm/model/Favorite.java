package com.gra.ecm.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Favorite {
    private Integer id;
    private Integer userId;
    private Integer productId;
    
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private String categoryName;
    private String image;
    private Integer status;
    private Integer sales;
}