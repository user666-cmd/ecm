package com.gra.ecm.dao;

import com.gra.ecm.model.News;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NewsMapper {
    List<News> findAll();
    int insert(News news);
    int updateById(News news);
    int deleteById(Integer id);
}