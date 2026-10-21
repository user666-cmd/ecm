package com.gra.ecm.api;

import com.gra.ecm.model.Favorite;
import com.gra.ecm.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品收藏管理API控制器
 * 提供商品收藏的增删查功能，支持用户收藏商品、取消收藏、查看收藏列表等操作
 */
@RestController
@RequestMapping(value = "/api/v1/favorites", produces = "application/json")
@CrossOrigin(origins = "*")
public class FavoriteApi {

    private FavoriteService favoriteService;

    /**
     * 依赖注入收藏服务层
     * @param favoriteService 收藏服务接口实现
     */
    @Autowired
    public void setFavoriteService(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    /**
     * 获取指定用户的所有收藏商品列表
     * @param userId 用户ID，通过请求参数传递
     * @return 返回该用户的所有收藏记录列表，HTTP状态码200
     */
    @GetMapping
    public ResponseEntity<List<Favorite>> getFavorites(@RequestParam Integer userId) {
        List<Favorite> favorites = favoriteService.findByUserId(userId);
        return ResponseEntity.ok(favorites);
    }

    /**
     * 添加商品到用户的收藏列表
     * 如果用户已经收藏过该商品，则不会重复添加
     * @param request 包含userId和productId的请求体，格式：{"userId": 用户ID, "productId": 商品ID}
     * @return 返回操作结果，包含成功状态、消息和收藏记录数据
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> addFavorite(@RequestBody Map<String, Integer> request) {
        Integer userId = request.get("userId");
        Integer productId = request.get("productId");

        Favorite favorite = favoriteService.addFavorite(userId, productId);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "收藏成功");
        response.put("data", favorite);
        return ResponseEntity.ok(response);
    }

    /**
     * 从用户的收藏列表中移除指定商品
     * 支持两种删除方式：通过用户ID和商品ID删除，或通过收藏记录ID删除
     * @param request 包含userId和productId的请求体，格式：{"userId": 用户ID, "productId": 商品ID}
     *               或者包含收藏记录ID：{"id": 收藏记录ID}
     * @return 返回操作结果，成功返回200，失败返回400
     */
    @DeleteMapping
    public ResponseEntity<Map<String, Object>> removeFavorite(@RequestBody Map<String, Integer> request) {
        Integer userId = request.get("userId");
        Integer productId = request.get("productId");

        boolean removed = favoriteService.removeFavorite(userId, productId);

        Map<String, Object> response = new HashMap<>();
        
        if (removed) {
            response.put("success", true);
            response.put("message", "取消收藏成功");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "取消收藏失败");
            return ResponseEntity.status(400).body(response);
        }
    }

    /**
     * 批量检查指定商品是否被用户收藏
     * 用于在商品列表页面显示收藏状态，提升用户体验
     * @param request 包含userId和productIds的请求体，格式：{"userId": 用户ID, "productIds": [商品ID1, 商品ID2, ...]}
     * @return 返回被收藏的商品记录列表，HTTP状态码200
     */
    @PostMapping("/check")
    public ResponseEntity<List<Favorite>> checkFavorites(@RequestBody Map<String, Object> request) {
        Integer userId = (Integer) request.get("userId");
        @SuppressWarnings("unchecked")
        List<Integer> productIds = (List<Integer>) request.get("productIds");

        List<Favorite> favorites = favoriteService.checkFavorites(userId, productIds);
        return ResponseEntity.ok(favorites);
    }
}