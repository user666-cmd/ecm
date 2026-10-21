package com.gra.ecm.service;

import com.gra.ecm.model.Like;

import java.util.List;

public interface LikeService {
    List<Like> findLikedNewsByUserId(Integer userId);
    boolean isLiked(Integer userId, Integer newsId);
    List<Like> checkLikes(Integer userId, List<Integer> newsIds);
    Like addLike(Integer userId, Integer newsId);
    boolean removeLike(Integer userId, Integer newsId);
}