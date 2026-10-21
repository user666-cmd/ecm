package com.gra.ecm.api;

import com.gra.ecm.model.Order;
import com.gra.ecm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单管理API控制器
 * 提供订单的增删改查功能，支持用户创建订单、取消订单、查看订单列表等操作
 * 包含订单状态管理、余额支付、库存扣减等核心业务逻辑
 */
@RestController
@RequestMapping(value = "/api/v1/orders", produces = "application/json")
@CrossOrigin(origins = "*")
public class OrderApi {

    private OrderService orderService;

    /**
     * 依赖注入订单服务层
     * @param orderService 订单服务接口实现
     */
    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * 获取指定用户的所有订单列表
     * 用于在个人中心的"我的订单"模块显示用户的所有订单记录
     * @param userId 用户ID，通过请求参数传递
     * @return 返回该用户的所有订单列表，HTTP状态码200
     */
    @GetMapping
    public ResponseEntity<List<Order>> getOrders(@RequestParam Integer userId) {
        List<Order> orders = orderService.findByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    /**
     * 根据订单ID获取单个订单详情
     * 用于查看订单的详细信息，包括订单状态、商品信息、收货地址等
     * @param id 订单ID，通过URL路径变量传递
     * @return 返回订单详情（状态码200）或404未找到
     */
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Integer id) {
        Order order = orderService.findById(id);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 创建新的订单
     * 这是系统的核心功能，涉及多个业务逻辑：
     * 1. 验证用户余额是否充足
     * 2. 冻结用户相应金额的余额
     * 3. 扣减商品库存，增加商品销量
     * 4. 创建订单记录和订单详情记录
     * 5. 如果库存为0，自动下架商品
     * 所有操作在事务中执行，保证数据一致性
     * @param order 订单信息对象，包含userId、addressId、orderItems等信息
     * @return 返回操作结果，包含成功状态、消息和创建后的订单数据
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "创建订单成功");
        response.put("data", createdOrder);
        return ResponseEntity.ok(response);
    }

    /**
     * 取消指定订单
     * 取消订单时会执行以下操作：
     * 1. 将订单状态设置为已取消
     * 2. 解冻用户余额，将冻结金额返还到可用余额
     * 3. 恢复商品库存，减少商品销量
     * 所有操作在事务中执行，保证数据一致性
     * @param id 订单ID，通过URL路径变量传递
     * @return 返回操作结果，成功返回200，失败返回400
     */
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Map<String, Object>> cancelOrder(@PathVariable Integer id) {
        boolean cancelled = orderService.cancelOrder(id);
        Map<String, Object> response = new HashMap<>();
        if (cancelled) {
            response.put("success", true);
            response.put("message", "取消订单成功");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "取消订单失败");
            return ResponseEntity.status(400).body(response);
        }
    }

    /**
     * 更新指定订单的信息
     * 用于修改订单的基本信息，如收货地址等
     * 注意：此方法不建议用于修改订单状态，应使用updateOrderStatus方法
     * @param id 订单ID，通过URL路径变量传递
     * @param order 更新的订单信息对象，通过请求体JSON格式传递
     * @return 返回操作结果，成功返回200，失败返回400
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateOrder(@PathVariable Integer id, @RequestBody Order order) {
        order.setId(id);
        boolean updated = orderService.updateOrder(order);
        Map<String, Object> response = new HashMap<>();
        if (updated) {
            response.put("success", true);
            response.put("message", "更新订单成功");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "更新订单失败");
            return ResponseEntity.status(400).body(response);
        }
    }

    /**
     * 更新指定订单的状态
     * 用于管理员或系统更新订单状态，如：待付款→已付款→已发货→已完成
     * 订单状态通常包括：0-待付款，1-已付款，2-已发货，3-已完成，4-已取消
     * @param id 订单ID，通过URL路径变量传递
     * @param request 包含status的请求体，格式：{"status": 状态值}
     * @return 返回操作结果，成功返回200，失败返回400
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<Map<String, Object>> updateOrderStatus(@PathVariable Integer id, @RequestBody Map<String, Integer> request) {
        Integer status = request.get("status");
        boolean updated = orderService.updateOrderStatus(id, status);
        Map<String, Object> response = new HashMap<>();
        if (updated) {
            response.put("success", true);
            response.put("message", "更新订单状态成功");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "更新订单状态失败");
            return ResponseEntity.status(400).body(response);
        }
    }

    /**
     * 删除指定订单
     * 物理删除订单记录，通常用于管理员清理历史订单数据
     * 注意：此操作不可恢复，建议使用取消订单功能而非直接删除
     * @param id 订单ID，通过URL路径变量传递
     * @return 返回操作结果，成功返回200，失败返回400
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteOrder(@PathVariable Integer id) {
        boolean deleted = orderService.deleteOrder(id);
        Map<String, Object> response = new HashMap<>();
        if (deleted) {
            response.put("success", true);
            response.put("message", "删除订单成功");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "删除订单失败");
            return ResponseEntity.status(400).body(response);
        }
    }
}