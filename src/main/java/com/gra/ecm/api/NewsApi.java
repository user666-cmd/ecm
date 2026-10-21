package com.gra.ecm.api;

import com.gra.ecm.model.News;
import com.gra.ecm.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资讯管理API控制器
 * 提供资讯的增删改查功能，支持管理员发布、编辑、删除资讯等操作
 */
@RestController
@RequestMapping(value = "/api/v1/news", produces = "application/json")
@CrossOrigin(origins = "*")
public class NewsApi {

    private NewsService newsService;

    /**
     * 依赖注入资讯服务层
     * @param newsService 资讯服务接口实现
     */
    @Autowired
    public void setNewsService(NewsService newsService) {
        this.newsService = newsService;
    }

    /**
     * 获取所有资讯列表
     * 用于在前端展示所有资讯，通常按时间倒序排列
     * @return 返回所有资讯的列表，HTTP状态码200
     */
    @GetMapping
    public ResponseEntity<List<News>> findAll() {
        List<News> newsList = newsService.findAll();
        return ResponseEntity.ok(newsList);
    }

    /**
     * 创建新的资讯
     * 支持管理员发布新的资讯内容，自动处理创建时间
     * @param newsRequest 包含资讯信息的请求体，格式：{"title": "标题", "content": "内容", "author": "作者", "createTime": "2024-01-01 12:00:00"}
     *                   createTime字段可选，如果不提供或格式错误则使用当前时间
     * @return 返回操作结果，包含成功状态、消息和创建后的资讯数据
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createNews(@RequestBody Map<String, String> newsRequest) {
        String title = newsRequest.get("title");
        String content = newsRequest.get("content");
        String author = newsRequest.get("author");
        String createTimeStr = newsRequest.get("createTime");

        News news = new News();
        news.setTitle(title);
        news.setContent(content);
        news.setAuthor(author);
        
        if (createTimeStr != null && !createTimeStr.isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                news.setCreateTime(LocalDateTime.parse(createTimeStr, formatter));
            } catch (Exception e) {
                news.setCreateTime(LocalDateTime.now());
            }
        } else {
            news.setCreateTime(LocalDateTime.now());
        }

        News createdNews = newsService.insert(news);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "添加成功");
        response.put("data", createdNews);
        return ResponseEntity.ok(response);
    }

    /**
     * 更新指定ID的资讯信息
     * 支持管理员编辑已发布的资讯内容，可以修改标题、内容、作者和创建时间
     * @param id 资讯ID，通过URL路径变量传递
     * @param newsRequest 包含更新信息的请求体，格式：{"title": "新标题", "content": "新内容", "author": "新作者", "createTime": "2024-01-01 12:00:00"}
     *                   createTime字段可选，如果不提供或格式错误则保持原值
     * @return 返回操作结果，包含成功状态、消息和更新后的资讯数据
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateNews(@PathVariable Integer id, @RequestBody Map<String, String> newsRequest) {
        String title = newsRequest.get("title");
        String content = newsRequest.get("content");
        String author = newsRequest.get("author");
        String createTimeStr = newsRequest.get("createTime");

        News news = new News();
        news.setId(id);
        news.setTitle(title);
        news.setContent(content);
        news.setAuthor(author);
        
        if (createTimeStr != null && !createTimeStr.isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                news.setCreateTime(LocalDateTime.parse(createTimeStr, formatter));
            } catch (Exception e) {
                
            }
        }

        News updatedNews = newsService.updateById(news);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "更新成功");
        response.put("data", updatedNews);
        return ResponseEntity.ok(response);
    }

    /**
     * 删除指定ID的资讯
     * 支持管理员删除已发布的资讯，删除后用户将无法查看该资讯
     * @param id 资讯ID，通过URL路径变量传递
     * @return 返回操作结果，成功返回200，失败返回400
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteNews(@PathVariable Integer id) {
        boolean deleted = newsService.deleteById(id);

        Map<String, Object> response = new HashMap<>();
        
        if (deleted) {
            response.put("success", true);
            response.put("message", "删除成功");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "删除失败");
            return ResponseEntity.status(400).body(response);
        }
    }
}