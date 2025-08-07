# 实体

## 1. 广告 (Adverts)
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
    extended_properties JSONB,  
    tags JSONB                   
);
```

## 2. 用户 (Users)
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

## 3. 分类 (Categories)
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

## 4. 广告统计 (Advert_Stats)
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

# API 接口

```bash
# 根据标签搜索广告
GET /api/adverts/search/tags?tag=促销

# 根据扩展属性搜索广告
GET /api/adverts/search/extended-property?property=target_audience&value=young_adults
```

# 广告管理 API

## CRUD
| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/api/adverts` | 获取所有广告 |
| GET | `/api/adverts/{id}` | 根据ID获取广告 |
| POST | `/api/adverts` | 创建新广告 |
| PUT | `/api/adverts/{id}` | 更新广告 |
| DELETE | `/api/adverts/{id}` | 删除广告 |

## 查询
| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/api/adverts/search/title?title=关键词` | 标题搜索 |
| GET | `/api/adverts/search/content?keyword=关键词` | 内容搜索 |
| GET | `/api/adverts/active` | 获取激活广告 |
| GET | `/api/adverts/category/{categoryId}` | 按分类查询 |
| GET | `/api/adverts/user/{userId}` | 按用户查询 |

## 统计

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/api/statistics/dashboard` | 获取仪表板数据 |
| GET | `/api/statistics/advert-status` | 广告状态统计 |
| GET | `/api/statistics/daily-stats` | 每日统计数据 |


## 创建广告
```bash
curl -X POST http://localhost:8080/api/adverts \
  -H "Content-Type: application/json" \
  -d '{
    "title": "",
    "content": "",
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
