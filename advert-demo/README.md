# 广告管理 Demo

这是一个基于 Spring Boot + PostgreSQL 的广告管理系统 demo。

## 技术栈

- Spring Boot 2.7.14
- Spring Data JPA
- PostgreSQL
- Maven

## 项目结构

```
advert-demo/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── example/
│       │           └── advert/
│       │               ├── DemoApplication.java          # 启动类
│       │               ├── controller/
│       │               │   └── AdvertController.java    # 控制器层
│       │               ├── service/
│       │               │   └── AdvertService.java       # 服务层
│       │               ├── dao/
│       │               │   └── AdvertRepository.java    # 数据访问层
│       │               └── model/
│       │                   └── Advert.java              # 实体类
│       └── resources/
│           └── application.yml                          # 配置文件
├── pom.xml
└── README.md
```

## 数据库配置

### 1. 安装 PostgreSQL

确保你的系统已安装 PostgreSQL，并启动服务。

### 2. 创建数据库

```sql
CREATE DATABASE advert_demo;
```

### 3. 修改配置

在 `src/main/resources/application.yml` 中修改数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/advert_demo
    username: your_username
    password: your_password
```

## 运行项目

### 1. 编译项目

```bash
mvn clean compile
```

### 2. 运行项目

```bash
mvn spring-boot:run
```

或者直接运行 `DemoApplication.java` 的 main 方法。

### 3. 访问接口

项目启动后，访问 `http://localhost:8080/api/adverts/health` 检查服务是否正常运行。

## API 接口

### 基础 CRUD 操作

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/api/adverts` | 获取所有广告 |
| GET | `/api/adverts/active` | 获取所有激活的广告 |
| GET | `/api/adverts/{id}` | 根据ID获取广告 |
| POST | `/api/adverts` | 创建新广告 |
| PUT | `/api/adverts/{id}` | 更新广告 |
| DELETE | `/api/adverts/{id}` | 删除广告（软删除） |

### 搜索功能

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/api/adverts/search/title?title=关键词` | 根据标题搜索 |
| GET | `/api/adverts/search/content?keyword=关键词` | 根据内容搜索 |

### 其他操作

| 方法 | 路径 | 描述 |
|------|------|------|
| DELETE | `/api/adverts/{id}/hard` | 硬删除广告 |
| PUT | `/api/adverts/{id}/activate` | 激活广告 |
| GET | `/api/adverts/health` | 健康检查 |

## 请求示例

### 创建广告

```bash
curl -X POST http://localhost:8080/api/adverts \
  -H "Content-Type: application/json" \
  -d '{
    "title": "测试广告",
    "content": "这是一个测试广告的内容",
    "imageUrl": "https://example.com/image.jpg"
  }'
```

### 查询广告

```bash
curl http://localhost:8080/api/adverts
```

### 更新广告

```bash
curl -X PUT http://localhost:8080/api/adverts/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "更新后的广告标题",
    "content": "更新后的内容",
    "imageUrl": "https://example.com/new-image.jpg"
  }'
```

## 数据库表结构

项目启动后会自动创建 `adverts` 表，结构如下：

```sql
CREATE TABLE adverts (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    image_url VARCHAR(500),
    created_time TIMESTAMP,
    updated_time TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);
```

## 注意事项

1. 确保 PostgreSQL 服务已启动
2. 确保数据库连接配置正确
3. 项目使用 JPA 自动创建表结构，无需手动建表
4. 删除操作默认为软删除（设置 is_active = false）
5. 如需硬删除，使用 `/api/adverts/{id}/hard` 接口

## 扩展功能

可以根据需要添加以下功能：

- 用户认证和权限控制
- 文件上传功能
- 广告分类管理
- 广告统计功能
- 前端页面 