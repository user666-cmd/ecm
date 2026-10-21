package com.gra.ecm.api;

import com.gra.ecm.model.Like;
import com.gra.ecm.service.LikeService;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资讯点赞管理API控制器
 * 提供资讯点赞的增删查功能，支持用户点赞资讯、取消点赞、查看点赞列表等操作
 */
@RestController
@RequestMapping("/api/v1/likes")
@CrossOrigin(origins = "*")
public class LikeApi {

    private LikeService likeService;

    /**
     * 依赖注入点赞服务层
     * @param likeService 点赞服务接口实现
     */
    @Autowired
    public void setLikeService(LikeService likeService) {
        this.likeService = likeService;
    }

    /**
     * 获取用户赞过的资讯列表
     * 用于在个人中心显示用户点赞过的所有资讯
     * @param userId 用户ID，通过请求参数传递
     * @return 返回该用户点赞过的所有资讯记录列表
     */
    @GetMapping("/news")
    public List<Like> getLikedNews(@RequestParam Integer userId) {
        return likeService.findLikedNewsByUserId(userId);
    }

    /**
     * 添加点赞
     * 用户对资讯进行点赞操作，如果已经点赞过则不会重复添加
     * @param request 包含userId和newsId的请求体，格式：{"userId": 用户ID, "newsId": 资讯ID}
     * @return 返回操作结果，包含成功状态和消息
     */
    @PostMapping
    public Map<String, Object> addLike(@RequestBody Map<String, Integer> request) {
        Integer userId = request.get("userId");
        Integer newsId = request.get("newsId");

        likeService.addLike(userId, newsId);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "点赞成功");
        return result;
    }

    /**
     * 取消点赞
     * 用户取消对资讯的点赞操作，删除对应的点赞记录
     * @param request 包含userId和newsId的请求体，格式：{"userId": 用户ID, "newsId": 资讯ID}
     * @return 返回操作结果，包含成功状态和消息
     */
    @DeleteMapping
    public Map<String, Object> removeLike(@RequestBody Map<String, Integer> request) {
        Integer userId = request.get("userId");
        Integer newsId = request.get("newsId");

        likeService.removeLike(userId, newsId);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "取消点赞成功");
        return result;
    }

    /**
     * 批量检查点赞状态
     * 用于在资讯列表页面显示点赞状态，提升用户体验
     * 返回格式为：[{"newsId": 资讯ID, "liked": true/false}, ...]
     * @param request 包含userId和newsIds的请求体，格式：{"userId": 用户ID, "newsIds": [资讯ID1, 资讯ID2, ...]}
     * @return 返回每个资讯的点赞状态列表，只包含已点赞的资讯
     */
    @PostMapping("/check")
    public List<Map<String, Object>> checkLikes(@RequestBody Map<String, Object> request) {
        Integer userId = (Integer) request.get("userId");
        List<Integer> newsIds = (List<Integer>) request.get("newsIds");

        List<Like> likedList = likeService.checkLikes(userId, newsIds);

        return likedList.stream()
                .map(like -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("newsId", like.getNewsId());
                    map.put("liked", true);
                    return map;
                })
                .toList();
    }
}