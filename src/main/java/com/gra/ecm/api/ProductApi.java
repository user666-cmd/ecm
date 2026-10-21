package com.gra.ecm.api;

import com.gra.ecm.model.Product;
import com.gra.ecm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品管理API控制器
 * 提供商品的增删改查功能，支持管理员管理商品、用户搜索和浏览商品等操作
 * 包含关键词搜索、分类筛选、价格排序等高级查询功能
 */
@RestController
@RequestMapping(value = "/api/v1/products", produces = "application/json")
@CrossOrigin(origins = "*")
public class ProductApi {
    private ProductService productService;

    /**
     * 依赖注入商品服务层
     * @param productService 商品服务接口实现
     */
    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 获取所有商品列表
     * 用于在前端展示所有商品，通常按时间倒序排列
     * @return 返回所有商品的列表，HTTP状态码200
     */
    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    /**
     * 搜索商品，支持关键词搜索、分类筛选和价格排序
     * 提供灵活的查询方式，用户可以组合使用不同的查询条件
     * @param keyword 搜索关键词，可选参数，用于搜索商品名称或描述
     * @param category 商品分类，可选参数，用于筛选特定分类的商品
     * @param priceSort 价格排序方式，可选参数，"asc"表示价格从低到高，"desc"表示价格从高到低
     * @return 返回符合查询条件的商品列表，HTTP状态码200
     */
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) String category,
        @RequestParam(required = false) String priceSort
    ) {
        List<Product> products;
        
        if (keyword != null && !keyword.trim().isEmpty() && category != null && !category.trim().isEmpty()) {
            products = productService.searchByKeywordAndCategory(keyword.trim(), category.trim());
        } else if (keyword != null && !keyword.trim().isEmpty()) {
            products = productService.searchByKeyword(keyword.trim());
        } else if (category != null && !category.trim().isEmpty()) {
            products = productService.searchByCategory(category.trim());
        } else {
            products = productService.findAll();
        }
        
        if ("asc".equals(priceSort)) {
            products.sort((p1, p2) -> p1.getPrice().compareTo(p2.getPrice()));
        } else if ("desc".equals(priceSort)) {
            products.sort((p1, p2) -> p2.getPrice().compareTo(p1.getPrice()));
        }
        
        return ResponseEntity.ok(products);
    }

    /**
     * 创建新的商品
     * 支持管理员添加新商品到系统中，包含完整的商品信息
     * @param productRequest 包含商品信息的请求体，格式：{"name": "商品名称", "description": "商品描述", "price": 99.99, "stock": 100, "categoryName": "汽车养护", "image": "图片URL", "status": 1, "sales": 0}
     *                   其中sales字段可选，默认为0；其他字段根据实际需求提供
     * @return 返回操作结果，包含成功状态、消息和创建后的商品数据
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createProduct(@RequestBody Map<String, Object> productRequest) {
        String name = (String) productRequest.get("name");
        String description = (String) productRequest.get("description");
        Double price = productRequest.get("price") != null ? ((Number) productRequest.get("price")).doubleValue() : null;
        Integer stock = productRequest.get("stock") != null ? ((Number) productRequest.get("stock")).intValue() : null;
        String categoryName = (String) productRequest.get("categoryName");
        String image = (String) productRequest.get("image");
        Integer status = productRequest.get("status") != null ? ((Number) productRequest.get("status")).intValue() : null;
        Integer sales = productRequest.get("sales") != null ? ((Number) productRequest.get("sales")).intValue() : 0;

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price != null ? java.math.BigDecimal.valueOf(price) : null);
        product.setStock(stock);
        product.setCategoryName(categoryName);
        product.setImage(image);
        product.setStatus(status);
        product.setSales(sales);

        Product createdProduct = productService.insert(product);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "添加成功");
        response.put("data", createdProduct);
        return ResponseEntity.ok(response);
    }

    /**
     * 更新指定ID的商品信息
     * 支持管理员编辑已存在的商品信息，可以修改商品的所有属性
     * @param id 商品ID，通过URL路径变量传递
     * @param productRequest 包含更新信息的请求体，格式：{"name": "新名称", "description": "新描述", "price": 199.99, "stock": 50, "categoryName": "汽车饰品", "image": "新图片URL", "status": 1, "sales": 10}
     *                   所有字段都是可选的，只更新提供的字段
     * @return 返回操作结果，包含成功状态、消息和更新后的商品数据
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateProduct(@PathVariable Integer id, @RequestBody Map<String, Object> productRequest) {
        String name = (String) productRequest.get("name");
        String description = (String) productRequest.get("description");
        Double price = productRequest.get("price") != null ? ((Number) productRequest.get("price")).doubleValue() : null;
        Integer stock = productRequest.get("stock") != null ? ((Number) productRequest.get("stock")).intValue() : null;
        String categoryName = (String) productRequest.get("categoryName");
        String image = (String) productRequest.get("image");
        Integer status = productRequest.get("status") != null ? ((Number) productRequest.get("status")).intValue() : null;
        Integer sales = productRequest.get("sales") != null ? ((Number) productRequest.get("sales")).intValue() : null;

        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price != null ? java.math.BigDecimal.valueOf(price) : null);
        product.setStock(stock);
        product.setCategoryName(categoryName);
        product.setImage(image);
        product.setStatus(status);
        product.setSales(sales);

        Product updatedProduct = productService.updateById(product);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "更新成功");
        response.put("data", updatedProduct);
        return ResponseEntity.ok(response);
    }

    /**
     * 删除指定ID的商品
     * 支持管理员删除不再销售的商品，删除后用户将无法查看和购买该商品
     * @param id 商品ID，通过URL路径变量传递
     * @return 返回操作结果，成功返回200，失败返回400
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable Integer id) {
        boolean deleted = productService.deleteById(id);

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