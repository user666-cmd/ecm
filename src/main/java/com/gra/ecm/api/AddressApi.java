package com.gra.ecm.api;

import com.gra.ecm.model.Address;
import com.gra.ecm.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收货地址管理API控制器
 * 提供收货地址的增删改查功能，包括设置默认地址等操作
 */
@RestController
@RequestMapping(value = "/api/v1/addresses", produces = "application/json")
@CrossOrigin(origins = "*")
public class AddressApi {

    private AddressService addressService;

    /**
     * 依赖注入地址服务层
     * @param addressService 地址服务接口实现
     */
    @Autowired
    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * 获取指定用户的所有收货地址列表
     * @param userId 用户ID，通过请求参数传递
     * @return 返回该用户的所有收货地址列表，HTTP状态码200
     */
    @GetMapping
    public ResponseEntity<List<Address>> getAddresses(@RequestParam Integer userId) {
        List<Address> addresses = addressService.findByUserId(userId);
        return ResponseEntity.ok(addresses);
    }

    /**
     * 根据地址ID获取单个收货地址详情
     * @param id 地址ID，通过URL路径变量传递
     * @return 返回地址详情（状态码200）或404未找到
     */
    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddress(@PathVariable Integer id) {
        Address address = addressService.findById(id);
        if (address != null) {
            return ResponseEntity.ok(address);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 添加新的收货地址
     * @param address 地址信息对象，通过请求体JSON格式传递
     * @return 返回操作结果，包含成功状态、消息和保存后的地址数据
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> addAddress(@RequestBody Address address) {
        Address savedAddress = addressService.addAddress(address);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "添加地址成功");
        response.put("data", savedAddress);
        return ResponseEntity.ok(response);
    }

    /**
     * 更新指定ID的收货地址信息
     * @param id 地址ID，通过URL路径变量传递
     * @param address 更新的地址信息对象，通过请求体JSON格式传递
     * @return 返回操作结果，成功返回200，失败返回400
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateAddress(@PathVariable Integer id, @RequestBody Address address) {
        address.setId(id);
        boolean updated = addressService.updateAddress(address);
        Map<String, Object> response = new HashMap<>();
        if (updated) {
            response.put("success", true);
            response.put("message", "更新地址成功");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "更新地址失败");
            return ResponseEntity.status(400).body(response);
        }
    }

    /**
     * 删除指定ID的收货地址
     * @param id 地址ID，通过URL路径变量传递
     * @return 返回操作结果，成功返回200，失败返回400
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteAddress(@PathVariable Integer id) {
        boolean deleted = addressService.deleteAddress(id);
        Map<String, Object> response = new HashMap<>();
        if (deleted) {
            response.put("success", true);
            response.put("message", "删除地址成功");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "删除地址失败");
            return ResponseEntity.status(400).body(response);
        }
    }

    /**
     * 设置指定地址为用户的默认收货地址
     * 该操作会将该用户的其他地址的默认状态设置为false，只保留一个默认地址
     * @param id 要设置为默认的地址ID，通过URL路径变量传递
     * @param request 包含userId的请求体，格式：{"userId": 用户ID}
     * @return 返回操作结果，成功返回200，失败返回400
     */
    @PutMapping("/{id}/default")
    public ResponseEntity<Map<String, Object>> setDefaultAddress(@PathVariable Integer id, @RequestBody Map<String, Integer> request) {
        Integer userId = request.get("userId");
        
        boolean setDefault = addressService.setDefaultAddress(userId, id);
        Map<String, Object> response = new HashMap<>();
        if (setDefault) {
            response.put("success", true);
            response.put("message", "设置默认地址成功");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "设置默认地址失败");
            return ResponseEntity.status(400).body(response);
        }
    }
}