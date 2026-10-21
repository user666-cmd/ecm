package com.gra.ecm.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Admin {
    private Integer id;            // 管理员ID（主键，自增）
    private String username;       // 登录账号（唯一，如：admin）
    private String password;       // 明文密码

}