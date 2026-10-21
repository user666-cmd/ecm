package com.gra.ecm.service.impl;

import com.gra.ecm.dao.LikeMapper;
import com.gra.ecm.model.Like;
import com.gra.ecm.service.LikeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class LikeServiceImpl implements LikeService {

    private LikeMapper likeMapper;

    @Autowired
    public void setLikeMapper(LikeMapper likeMapper) {
        this.likeMapper = likeMapper;
    }

    @Override
    public List<Like> findLikedNewsByUserId(Integer userId) {
        return likeMapper.findLikedNewsByUserId(userId);
    }

    @Override
    public boolean isLiked(Integer userId, Integer newsId) {
        Like like = likeMapper.findByUserIdAndNewsId(userId, newsId);
        return like != null;
    }

    @Override
    public List<Like> checkLikes(Integer userId, List<Integer> newsIds) {
        if (newsIds == null || newsIds.isEmpty()) {
            return List.of();
        }
        return likeMapper.checkLikes(userId, newsIds);
    }

    @Override
    @Transactional
    public Like addLike(Integer userId, Integer newsId) {
        Like existing = likeMapper.findByUserIdAndNewsId(userId, newsId);
        if (existing != null) {
            return existing;
        }

        Like like = new Like();
        like.setUserId(userId);
        like.setNewsId(newsId);
        likeMapper.insert(like);
        return like;
    }

    @Override
    @Transactional
    public boolean removeLike(Integer userId, Integer newsId) {
        int deleted = likeMapper.deleteByUserIdAndNewsId(userId, newsId);
        return deleted > 0;
    }
}