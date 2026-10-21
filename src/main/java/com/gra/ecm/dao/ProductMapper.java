package com.gra.ecm.dao;

import com.gra.ecm.model.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<Product> findAll();
    List<Product> searchByKeyword(String keyword);
    List<Product> searchByCategory(String categoryName);
    List<Product> searchByKeywordAndCategory(String keyword, String categoryName);
    int insert(Product product);
    int updateById(Product product);
    int deleteById(Integer id);
    int updateStockAndSales(@org.apache.ibatis.annotations.Param("productId") Integer productId, @org.apache.ibatis.annotations.Param("quantity") Integer quantity);
    int updateStockAndSalesReverse(@org.apache.ibatis.annotations.Param("productId") Integer productId, @org.apache.ibatis.annotations.Param("quantity") Integer quantity);
}