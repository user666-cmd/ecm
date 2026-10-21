package com.gra.ecm.service.impl;

import com.gra.ecm.dao.NewsMapper;
import com.gra.ecm.model.News;
import com.gra.ecm.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    private NewsMapper newsMapper;

    @Autowired
    public void setNewsMapper(NewsMapper newsMapper) {
        this.newsMapper = newsMapper;
    }

    @Override
    public List<News> findAll() {
        return newsMapper.findAll();
    }

    @Override
    public News insert(News news) {
        if (news.getCreateTime() == null) {
            news.setCreateTime(LocalDateTime.now());
        }
        newsMapper.insert(news);
        return news;
    }

    @Override
    public News updateById(News news) {
        newsMapper.updateById(news);
        return news;
    }

    @Override
    public boolean deleteById(Integer id) {
        return newsMapper.deleteById(id) > 0;
    }
}