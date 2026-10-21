# ecm - 汽车用品电商管理系统（后端）

基于 Spring Boot 的电商管理系统后端服务，为前端 [shop-web](https://github.com/user666-cmd/shop-web) 提供 RESTful API。

## 技术栈

| 分类 | 技术 | 版本 |
|------|------|------|
| 基础框架 | Spring Boot | 4.0.5 |
| 语言 | Java | 21 |
| Web | Spring MVC | 内嵌于 Spring Boot |
| ORM | MyBatis | 4.0.1 |
| 数据库 | MySQL | 8.x |
| 分页 | PageHelper | 2.1.1 |
| 简化代码 | Lombok | 内嵌 |
| 热部署 | spring-boot-devtools | runtime |

## 功能模块

按业务域划分的接口（对应 `com.gra.ecm.api` 包）：

- **User**：用户注册、登录、个人信息管理
- **Admin**：管理员后台接口
- **Product**：商品管理、商品列表与详情
- **Order / OrderItem**：下单、订单列表、订单项
- **Address**：收货地址管理
- **Favorite**：用户收藏
- **Like**：商品点赞
- **News**：资讯/公告
- **UserBalance**：用户余额

## 项目结构

```
src/main/java/com/gra/ecm/
├── EcmApplication.java        # 启动入口
├── api/                        # Controller 层（接口）
├── service/                    # Service 接口
│   └── impl/                    # Service 实现
├── dao/                        # MyBatis Mapper 接口
└── model/                      # 实体类
src/main/resources/
├── application.yml             # 配置文件
└── mapper/                     # MyBatis XML 映射
```

## 本地运行

### 环境要求

- JDK 21+
- Maven 3.8+（或使用项目自带的 `mvnw`）
- MySQL 8.x

### 步骤

1. **创建数据库**：在 MySQL 中创建名为 `ecm` 的数据库，并导入建表脚本（见下方说明）。

2. **修改数据库连接**：编辑 `src/main/resources/application.yml`，将账号密码改为你本地的：

   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/ecm?characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true
       username: root        # 改成你的 MySQL 账号
       password: 你的密码    # 改成你的 MySQL 密码
   ```

   > 注意：仓库中提交的是本地开发用的示例配置，**生产环境请勿使用弱密码**，建议改用环境变量注入。

3. **启动后端**：

   ```bash
   # 方式一：使用 Maven
   mvn spring-boot:run

   # 方式二：使用项目自带 Maven Wrapper（Windows）
   mvnw.cmd spring-boot:run
   ```

   启动后服务监听 `http://localhost:8080`，接口统一前缀为 `/api/v1`。

### 建表脚本

> ⚠️ 本仓库暂未包含 SQL 建表脚本。表结构可根据 `src/main/resources/mapper/*.xml` 与 `src/main/java/com/gra/ecm/model/` 下的实体类反推，或从实际开发用的 Navicat / MySQL Workbench 中导出 `schema.sql` 后补充进 `sql/` 目录。

## 接口约定

- 请求前缀：`/api/v1`
- 前端开发服务器（Vite，端口 5000）会将 `/api/**` 代理到本服务的 `/api/v1/**`。

## 开源协议

[MIT License](./LICENSE)
