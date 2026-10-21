package com.gra.ecm.service;

import com.gra.ecm.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    List<Product> searchByKeyword(String keyword);
    List<Product> searchByCategory(String categoryName);
    List<Product> searchByKeywordAndCategory(String keyword, String categoryName);
    Product insert(Product product);
    Product updateById(Product product);
    boolean deleteById(Integer id);
}