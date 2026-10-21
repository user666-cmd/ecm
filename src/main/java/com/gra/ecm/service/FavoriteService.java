package com.gra.ecm.service;

import com.gra.ecm.model.Favorite;

import java.util.List;

public interface FavoriteService {
    List<Favorite> findByUserId(Integer userId);
    boolean isFavorite(Integer userId, Integer productId);
    List<Favorite> checkFavorites(Integer userId, List<Integer> productIds);
    Favorite addFavorite(Integer userId, Integer productId);
    boolean removeFavorite(Integer userId, Integer productId);
}