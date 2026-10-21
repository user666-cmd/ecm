package com.gra.ecm.dao;

import com.gra.ecm.model.Like;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LikeMapper {
    List<Like> findLikedNewsByUserId(Integer userId);
    Like findByUserIdAndNewsId(Integer userId, Integer newsId);
    List<Like> checkLikes(Integer userId, List<Integer> newsIds);
    int insert(Like like);
    int deleteByUserIdAndNewsId(Integer userId, Integer newsId);
}