package com.gra.ecm.service.impl;

import com.gra.ecm.dao.FavoriteMapper;
import com.gra.ecm.model.Favorite;
import com.gra.ecm.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteMapper favoriteMapper;

    @Autowired
    public void setFavoriteMapper(FavoriteMapper favoriteMapper) {
        this.favoriteMapper = favoriteMapper;
    }

    @Override
    public List<Favorite> findByUserId(Integer userId) {
        return favoriteMapper.findByUserId(userId);
    }

    @Override
    public boolean isFavorite(Integer userId, Integer productId) {
        Favorite favorite = favoriteMapper.findByUserIdAndProductId(userId, productId);
        return favorite != null;
    }

    @Override
    public List<Favorite> checkFavorites(Integer userId, List<Integer> productIds) {
        return favoriteMapper.checkFavorites(userId, productIds);
    }

    @Override
    public Favorite addFavorite(Integer userId, Integer productId) {
        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setProductId(productId);
        favoriteMapper.insert(favorite);
        return favorite;
    }

    @Override
    public boolean removeFavorite(Integer userId, Integer productId) {
        return favoriteMapper.deleteByUserIdAndProductId(userId, productId) > 0;
    }
}