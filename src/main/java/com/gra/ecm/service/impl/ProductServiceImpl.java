package com.gra.ecm.service.impl;

import com.gra.ecm.dao.ProductMapper;
import com.gra.ecm.model.Product;
import com.gra.ecm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductMapper productMapper;

    @Autowired
    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public List<Product> findAll() {
        return productMapper.findAll();
    }

    @Override
    public List<Product> searchByKeyword(String keyword) {
        return productMapper.searchByKeyword(keyword);
    }

    @Override
    public List<Product> searchByCategory(String categoryName) {
        return productMapper.searchByCategory(categoryName);
    }

    @Override
    public List<Product> searchByKeywordAndCategory(String keyword, String categoryName) {
        return productMapper.searchByKeywordAndCategory(keyword, categoryName);
    }

    @Override
    public Product insert(Product product) {
        productMapper.insert(product);
        return product;
    }

    @Override
    public Product updateById(Product product) {
        productMapper.updateById(product);
        return product;
    }

    @Override
    public boolean deleteById(Integer id) {
        return productMapper.deleteById(id) > 0;
    }
}