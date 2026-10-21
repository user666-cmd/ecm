package com.gra.ecm.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Product {
    private Integer id;            // 商品ID（主键）
    private String name;           // 商品名称（如：全包围汽车脚垫）
    private String description;    // 商品描述/详情
    private BigDecimal price;      // 价格
    private Integer stock;         // 库存
    private String categoryName;   // 分类名称
    private String image;          // 商品封面图片
    private Integer status;        // 商品状态 0=下架 1=上架
    private Integer sales;         // 销量（用于统计、排序）

    //以上为私有属性，外部无法直接访问，需通过getter方法和setter方法访问
}