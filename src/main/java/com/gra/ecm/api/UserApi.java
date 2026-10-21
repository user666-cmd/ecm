package com.gra.ecm.api;

import com.gra.ecm.model.Admin;
import com.gra.ecm.model.User;
import com.gra.ecm.service.AdminService;
import com.gra.ecm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户认证和管理API控制器
 * 提供用户登录、注册、用户管理等功能
 * 同时处理普通用户和管理员的身份认证
 * 包含用户CRUD操作和管理员用户管理功能
 */
@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
@CrossOrigin(origins = "*")
public class UserApi {

    private UserService userService;
    private AdminService adminService;

    /**
     * 依赖注入用户服务层
     * @param userService 用户服务接口实现
     */
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 依赖注入管理员服务层
     * @param adminService 管理员服务接口实现
     */
    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * 用户登录接口
     * 根据用户名自动判断是普通用户还是管理员，分别进行不同的登录验证
     * 管理员用户名固定为"admin"，其他用户名都视为普通用户
     * @param loginRequest 包含用户名和密码的请求体，格式：{"username": "用户名", "password": "密码"}
     * @return 返回登录结果，成功返回用户信息和管理员信息，失败返回401状态码
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        Map<String, Object> response = new HashMap<>();
        
        if ("admin".equals(username)) {
            Admin admin = adminService.login(username, password);
            if (admin != null) {
                response.put("success", true);
                response.put("message", "管理员登录成功");
                response.put("data", Map.of(
                    "id", admin.getId(),
                    "username", admin.getUsername()
                ));
                return ResponseEntity.ok(response);
            }
        } else {
            User user = userService.login(username, password);
            if (user != null) {
                response.put("success", true);
                response.put("message", "登录成功");
                response.put("data", Map.of(
                    "id", user.getId(),
                    "username", user.getUsername(),
                    "phone", user.getPhone(),
                    "email", user.getEmail()
                ));
                return ResponseEntity.ok(response);
            }
        }
        
        response.put("success", false);
        response.put("message", "用户名或密码错误");
        return ResponseEntity.status(401).body(response);
    }

    /**
     * 用户注册接口
     * 新用户注册功能，创建新的普通用户账号
     * 会检查用户名是否已存在，如果存在则返回失败
     * @param registerRequest 包含用户信息的请求体，格式：{"username": "用户名", "password": "密码", "phone": "手机号", "email": "邮箱"}
     * @return 返回注册结果，成功返回用户信息，失败返回400状态码和错误信息
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, String> registerRequest) {
        String username = registerRequest.get("username");
        String password = registerRequest.get("password");
        String phone = registerRequest.get("phone");
        String email = registerRequest.get("email");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPhone(phone);
        user.setEmail(email);

        User registeredUser = userService.register(user);

        Map<String, Object> response = new HashMap<>();
        
        if (registeredUser != null) {
            response.put("success", true);
            response.put("message", "注册成功");
            response.put("data", Map.of(
                "id", registeredUser.getId(),
                "username", registeredUser.getUsername(),
                "phone", registeredUser.getPhone(),
                "email", registeredUser.getEmail()
            ));
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "用户名已存在");
            return ResponseEntity.status(400).body(response);
        }
    }

    /**
     * 获取所有用户列表
     * 用于管理员查看系统中的所有普通用户信息
     * @return 返回所有普通用户的列表，HTTP状态码200
     */
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    /**
     * 创建新用户
     * 管理员功能，用于管理员创建新的普通用户账号
     * 与注册功能类似，但是由管理员操作而非用户自主注册
     * @param userRequest 包含用户信息的请求体，格式：{"username": "用户名", "password": "密码", "phone": "手机号", "email": "邮箱"}
     * @return 返回创建结果，成功返回用户信息，失败返回400状态码和错误信息
     */
    @PostMapping("/users")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody Map<String, String> userRequest) {
        String username = userRequest.get("username");
        String password = userRequest.get("password");
        String phone = userRequest.get("phone");
        String email = userRequest.get("email");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPhone(phone);
        user.setEmail(email);

        User createdUser = userService.register(user);

        Map<String, Object> response = new HashMap<>();
        
        if (createdUser != null) {
            response.put("success", true);
            response.put("message", "添加成功");
            response.put("data", Map.of(
                "id", createdUser.getId(),
                "username", createdUser.getUsername(),
                "phone", createdUser.getPhone(),
                "email", createdUser.getEmail()
            ));
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "用户名已存在");
            return ResponseEntity.status(400).body(response);
        }
    }

    /**
     * 更新指定用户的信息
     * 管理员功能，用于管理员修改普通用户的信息
     * 密码字段是可选的，如果不提供或为空则保持原密码不变
     * @param id 用户ID，通过URL路径变量传递
     * @param userRequest 包含更新信息的请求体，格式：{"username": "新用户名", "password": "新密码", "phone": "新手机号", "email": "新邮箱"}
     *                   其中password字段可选，其他字段根据实际需求提供
     * @return 返回更新结果，包含成功状态、消息和更新后的用户数据
     */
    @PutMapping("/users/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Integer id, @RequestBody Map<String, String> userRequest) {
        String username = userRequest.get("username");
        String password = userRequest.get("password");
        String phone = userRequest.get("phone");
        String email = userRequest.get("email");

        User user = new User();
        user.setId(id);
        user.setUsername(username);
        if (password != null && !password.isEmpty()) {
            user.setPassword(password);
        }
        user.setPhone(phone);
        user.setEmail(email);

        User updatedUser = userService.updateById(user);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "更新成功");
        response.put("data", Map.of(
            "id", updatedUser.getId(),
            "username", updatedUser.getUsername(),
            "phone", updatedUser.getPhone(),
            "email", updatedUser.getEmail()
        ));
        return ResponseEntity.ok(response);
    }

    /**
     * 删除指定用户
     * 管理员功能，用于管理员删除不再需要的普通用户账号
     * 注意：此操作不可恢复，删除后用户将无法登录
     * @param id 用户ID，通过URL路径变量传递
     * @return 返回删除结果，成功返回200，失败返回400
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Integer id) {
        boolean deleted = userService.deleteById(id);

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