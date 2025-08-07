# 广告管理系统 Demo

这是一个基于 Spring Boot + PostgreSQL 的完整广告管理系统，包含用户管理、分类管理、广告统计等功能的演示项目。

## 🚀 技术栈

- **后端框架**: Spring Boot 2.7.14
- **数据库**: PostgreSQL 13
- **ORM框架**: Spring Data JPA + Hibernate
- **构建工具**: Maven
- **容器化**: Docker + Docker Compose
- **CI/CD**: Drone CI
- **Java版本**: Java 8

## 📁 项目结构

```
advert-demo/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/example/advert/
│       │       ├── DemoApplication.java              # 启动类
│       │       ├── controller/
│       │       │   ├── AdvertController.java        # 广告控制器
│       │       │   └── StatisticsController.java    # 统计控制器
│       │       ├── service/
│       │       │   └── AdvertService.java           # 广告服务层
│       │       ├── dao/
│       │       │   ├── AdvertRepository.java        # 广告数据访问层
│       │       │   ├── UserRepository.java          # 用户数据访问层
│       │       │   ├── CategoryRepository.java      # 分类数据访问层
│       │       │   └── AdvertStatsRepository.java  # 统计数据访问层
│       │       ├── model/
│       │       │   ├── Advert.java                 # 广告实体
│       │       │   ├── User.java                   # 用户实体
│       │       │   ├── Category.java               # 分类实体
│       │       │   └── AdvertStats.java           # 统计实体
│       │       └── config/
│       │           └── DataInitializer.java        # 数据初始化器
│       └── resources/
│           ├── application.yml                      # 配置文件
│           └── data.sql                            # 初始数据
├── Dockerfile                                     # Docker镜像配置
├── docker-compose.yml                             # Docker Compose配置
├── .drone.yml                                     # Drone CI配置
├── pom.xml
└── README.md
```

## 🗄️ 数据库设计

### 核心实体

#### 1. 广告 (Adverts)
```sql
CREATE TABLE adverts (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    image_url VARCHAR(500),
    category_id BIGINT,
    user_id BIGINT,
    status VARCHAR(255),
    priority INTEGER,
    budget DOUBLE PRECISION,
    spent_amount DOUBLE PRECISION,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    created_time TIMESTAMP,
    updated_time TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE,
    extended_properties JSONB,  -- JSON扩展属性
    tags JSONB                   -- JSON标签数组
);
```

#### 2. 用户 (Users)
```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    real_name VARCHAR(50),
    phone VARCHAR(20),
    role VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    last_login_time TIMESTAMP,
    created_time TIMESTAMP,
    updated_time TIMESTAMP
);
```

#### 3. 分类 (Categories)
```sql
CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    parent_id BIGINT,
    level INTEGER,
    sort_order INTEGER,
    is_active BOOLEAN DEFAULT TRUE,
    created_time TIMESTAMP,
    updated_time TIMESTAMP
);
```

#### 4. 广告统计 (Advert_Stats)
```sql
CREATE TABLE advert_stats (
    id BIGSERIAL PRIMARY KEY,
    advert_id BIGINT NOT NULL,
    stats_date DATE NOT NULL,
    view_count BIGINT,
    click_count BIGINT,
    ctr DOUBLE PRECISION,
    created_time TIMESTAMP,
    updated_time TIMESTAMP
);
```

## 🚀 快速开始

### 方式一：本地运行

#### 1. 环境准备
- Java 8+
- Maven 3.6+
- PostgreSQL 13+

#### 2. 数据库配置
```sql
CREATE DATABASE advert_demo;
```

修改 `src/main/resources/application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/advert_demo
    username: postgres
    password: your_password
```

#### 3. 构建运行
```bash
# 编译项目
mvn clean package -DskipTests

# 运行应用
java -jar target/advert-demo-1.0.0.jar
```

### 方式二：Docker 部署

#### 1. 构建镜像
```bash
docker build -t advert-demo .
```

#### 2. 使用 Docker Compose
```bash
# 启动所有服务
docker-compose up -d

# 查看日志
docker-compose logs -f

# 停止服务
docker-compose down
```

## 📡 API 接口

### JSON 功能支持

本项目支持 PostgreSQL 的 JSON/JSONB 数据类型，提供灵活的扩展属性存储和查询功能：

#### JSON 字段说明
- **extended_properties**: 存储广告的扩展属性，如目标受众、季节、产品类型等
- **tags**: 存储广告标签数组，支持标签搜索

#### JSON 查询示例
```bash
# 根据标签搜索广告
GET /api/adverts/search/tags?tag=促销

# 根据扩展属性搜索广告
GET /api/adverts/search/extended-property?property=target_audience&value=young_adults
```

#### JSON 数据示例
```json
{
  "extended_properties": {
    "target_audience": "young_adults",
    "season": "spring",
    "discount_type": "percentage"
  },
  "tags": ["促销", "春季", "新品"]
}
```

### 广告管理 API

#### 基础 CRUD
| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/api/adverts` | 获取所有广告 |
| GET | `/api/adverts/{id}` | 根据ID获取广告 |
| POST | `/api/adverts` | 创建新广告 |
| PUT | `/api/adverts/{id}` | 更新广告 |
| DELETE | `/api/adverts/{id}` | 删除广告 |

#### 高级查询
| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/api/adverts/search/title?title=关键词` | 标题搜索 |
| GET | `/api/adverts/search/content?keyword=关键词` | 内容搜索 |
| GET | `/api/adverts/active` | 获取激活广告 |
| GET | `/api/adverts/category/{categoryId}` | 按分类查询 |
| GET | `/api/adverts/user/{userId}` | 按用户查询 |

### 统计 API

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/api/statistics/dashboard` | 获取仪表板数据 |
| GET | `/api/statistics/advert-status` | 广告状态统计 |
| GET | `/api/statistics/daily-stats` | 每日统计数据 |

## 💡 使用示例

### 创建广告
```bash
curl -X POST http://localhost:8080/api/adverts \
  -H "Content-Type: application/json" \
  -d '{
    "title": "春季促销活动",
    "content": "春季新品上市，全场8折，精选商品，限时抢购，机会难得！",
    "imageUrl": "https://example.com/images/spring-sale.jpg",
    "categoryId": 1,
    "userId": 1,
    "status": "APPROVED",
    "priority": 1,
    "budget": 1000.0,
    "startTime": "2025-03-01T00:00:00",
    "endTime": "2025-03-31T23:59:59"
  }'
```

### 查询广告
```bash
# 获取所有广告
curl http://localhost:8080/api/adverts

# 根据ID获取广告
curl http://localhost:8080/api/adverts/1

# 搜索广告
curl "http://localhost:8080/api/adverts/search/title?title=促销"
```

### 获取统计数据
```bash
# 获取仪表板数据
curl http://localhost:8080/api/statistics/dashboard

# 获取广告状态统计
curl http://localhost:8080/api/statistics/advert-status
```

## 🔧 高级功能

### 复杂查询示例

项目包含大量复杂的SQL查询，用于业务分析：

1. **广告创建时间分布统计**
2. **预算使用率分析**
3. **点击率异常检测**
4. **分类层级查询**
5. **用户活跃度统计**

### 数据初始化

项目启动时会自动：
1. 创建数据库表结构
2. 插入测试数据（用户、分类、广告、统计数据）
3. 建立关联关系

## 🐳 Docker 部署

### 生产环境 Dockerfile
```dockerfile
FROM openjdk:8-jre-alpine
WORKDIR /app
COPY target/advert-demo-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Docker Compose 配置
```yaml
version: '3.8'
services:
  postgres:
    image: postgres:13-alpine
    environment:
      POSTGRES_DB: advert_demo
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: password
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

  advert-app:
    build: .
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/advert_demo
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: password
    depends_on:
      - postgres
```

## 🔄 CI/CD 配置

### Drone CI 配置
项目包含 `.drone.yml` 配置文件，支持：
- 自动构建
- 单元测试
- Docker 镜像构建
- 自动部署

## 📊 监控和日志

### 健康检查
```bash
curl http://localhost:8080/api/adverts/health
```

### 日志级别
在 `application.yml` 中配置：
```yaml
logging:
  level:
    com.example.advert: DEBUG
    org.hibernate.SQL: DEBUG
    org.hibernate.type.descriptor.sql.BasicBinder: TRACE
```

## 🛠️ 故障排除

### 常见问题

1. **端口占用**
   ```bash
   # 检查端口占用
   netstat -an | findstr ":8080"
   
   # 停止占用进程
   taskkill /F /PID <进程ID>
   ```

2. **数据库连接失败**
   - 检查 PostgreSQL 服务是否启动
   - 验证数据库连接配置
   - 确认数据库用户权限

3. **HQL 查询错误**
   - 检查实体类字段映射
   - 验证查询语法
   - 查看详细错误日志

## 📝 开发说明

### 代码规范
- 使用 Java 8 语法
- 遵循 Spring Boot 最佳实践
- 统一的异常处理
- 完整的 API 文档

### 扩展建议
1. 添加用户认证和权限控制
2. 实现文件上传功能
3. 添加缓存机制
4. 集成消息队列
5. 添加前端界面

## 📄 许可证

本项目仅用于学习和演示目的。

## 🤝 贡献

欢迎提交 Issue 和 Pull Request！

---

**项目状态**: ✅ 正常运行  
**最后更新**: 2025-08-06  
**版本**: 1.0.0 