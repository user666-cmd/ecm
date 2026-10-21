package com.gra.ecm.service;

import com.gra.ecm.model.News;

import java.util.List;

public interface NewsService {
    List<News> findAll();
    News insert(News news);
    News updateById(News news);
    boolean deleteById(Integer id);
}