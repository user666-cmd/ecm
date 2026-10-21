package com.gra.ecm.dao;

import com.gra.ecm.model.Favorite;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FavoriteMapper {
    List<Favorite> findByUserId(Integer userId);
    Favorite findByUserIdAndProductId(Integer userId, Integer productId);
    List<Favorite> checkFavorites(Integer userId, List<Integer> productIds);
    int insert(Favorite favorite);
    int deleteByUserIdAndProductId(Integer userId, Integer productId);
}