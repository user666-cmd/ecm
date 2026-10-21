package com.gra.ecm.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Integer id;            // 用户ID（主键，自增）
    private String username;       // 用户名（唯一，登录账号）
    private String password;       // 明文密码
    private String phone;          // 手机号（唯一，如：13800138000）
    private String email;          // 邮箱（唯一，如：user@example.com）

}