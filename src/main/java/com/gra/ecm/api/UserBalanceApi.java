package com.gra.ecm.api;

import com.gra.ecm.model.UserBalance;
import com.gra.ecm.dao.UserBalanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户余额管理API控制器
 * 提供用户余额查询和充值功能
 * 采用余额冻结机制确保支付安全，支持余额自动创建和充值验证
 */
@RestController
@RequestMapping(value = "/api/v1/balance", produces = "application/json")
@CrossOrigin(origins = "*")
public class UserBalanceApi {

    private UserBalanceMapper userBalanceMapper;

    /**
     * 依赖注入用户余额数据访问层
     * 注意：这里直接注入Mapper而不是Service，简化了层次结构
     * @param userBalanceMapper 用户余额Mapper接口
     */
    @Autowired
    public void setUserBalanceMapper(UserBalanceMapper userBalanceMapper) {
        this.userBalanceMapper = userBalanceMapper;
    }

    /**
     * 获取用户余额信息
     * 如果用户余额记录不存在，则自动创建一个新的余额记录
     * 这种设计确保每个用户都有余额记录，简化了前端逻辑
     * @param userId 用户ID，通过请求参数传递
     * @return 返回用户余额信息，包含可用余额和冻结金额，HTTP状态码200
     */
    @GetMapping
    public ResponseEntity<UserBalance> getBalance(@RequestParam Integer userId) {
        UserBalance balance = userBalanceMapper.findByUserId(userId);
        if (balance != null) {
            return ResponseEntity.ok(balance);
        } else {
            UserBalance newBalance = new UserBalance();
            newBalance.setUserId(userId);
            newBalance.setBalance(BigDecimal.ZERO);
            newBalance.setFrozenAmount(BigDecimal.ZERO);
            userBalanceMapper.insert(newBalance);
            return ResponseEntity.ok(newBalance);
        }
    }

    /**
     * 用户余额充值
     * 支持用户向账户充值，充值金额直接增加到可用余额
     * 包含金额验证和自动创建余额记录的功能
     * @param request 包含userId和amount的请求体，格式：{"userId": 用户ID, "amount": 充值金额}
     * @return 返回充值结果，成功返回充值金额，失败返回400状态码和错误信息
     */
    @PostMapping("/recharge")
    public ResponseEntity<Map<String, Object>> recharge(@RequestBody Map<String, Object> request) {
        Integer userId = (Integer) request.get("userId");
        BigDecimal amount = new BigDecimal(request.get("amount").toString());
        
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "充值金额必须大于0");
            return ResponseEntity.status(400).body(errorResponse);
        }
        
        UserBalance balance = userBalanceMapper.findByUserId(userId);
        if (balance == null) {
            UserBalance newBalance = new UserBalance();
            newBalance.setUserId(userId);
            newBalance.setBalance(amount);
            newBalance.setFrozenAmount(BigDecimal.ZERO);
            userBalanceMapper.insert(newBalance);
        } else {
            userBalanceMapper.updateBalance(userId, amount);
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "充值成功");
        response.put("amount", amount);
        return ResponseEntity.ok(response);
    }
}