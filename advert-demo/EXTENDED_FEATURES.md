# 广告管理系统扩展功能

## 🎯 扩展概述

我们已经将原本简单的广告管理系统扩展为一个功能丰富的企业级应用，包含用户管理、分类管理、统计分析等复杂业务场景。

## 📊 新增实体类

### 1. 用户管理 (User)
- **用户角色**: ADMIN（管理员）、EDITOR（编辑）、USER（普通用户）
- **用户信息**: 用户名、密码、邮箱、真实姓名、电话等
- **登录记录**: 最后登录时间、账户状态等

### 2. 分类管理 (Category)
- **多级分类**: 支持父子分类，无限层级
- **分类属性**: 名称、描述、级别、排序、激活状态
- **树形结构**: 支持分类树查询和路径查询

### 3. 广告统计 (AdvertStats)
- **统计数据**: 展示次数、点击次数、点击率
- **时间维度**: 按日期统计，支持趋势分析
- **性能指标**: CTR（点击率）自动计算

### 4. 扩展广告 (Advert)
- **审核流程**: DRAFT、PENDING、APPROVED、REJECTED、ACTIVE、PAUSED、COMPLETED
- **预算管理**: 预算金额、已花费金额
- **投放控制**: 开始时间、结束时间、优先级
- **关联关系**: 分类ID、用户ID

## 🔍 复杂 SQL 查询

### 用户相关查询
```sql
-- 统计各角色用户数量
SELECT u.role, COUNT(u) FROM User u WHERE u.isActive = true GROUP BY u.role

-- 查找最近登录的用户
SELECT u FROM User u WHERE u.lastLoginTime >= :since ORDER BY u.lastLoginTime DESC

-- 统计各时间段注册的用户数量
SELECT DATE(u.createdTime), COUNT(u) FROM User u 
WHERE u.createdTime >= :startDate 
GROUP BY DATE(u.createdTime) 
ORDER BY DATE(u.createdTime)
```

### 分类相关查询
```sql
-- 查找分类树（包含子分类数量）
SELECT c, (SELECT COUNT(sc) FROM Category sc WHERE sc.parentId = c.id AND sc.isActive = true) 
FROM Category c WHERE c.parentId IS NULL AND c.isActive = true ORDER BY c.sortOrder

-- 统计各分类下的广告数量
SELECT c.name, COUNT(a) FROM Category c 
LEFT JOIN Advert a ON c.id = a.categoryId AND a.isActive = true 
WHERE c.isActive = true 
GROUP BY c.id, c.name 
ORDER BY COUNT(a) DESC
```

### 广告相关查询
```sql
-- 多条件复杂查询
SELECT a FROM Advert a WHERE 
(:categoryId IS NULL OR a.categoryId = :categoryId) AND 
(:userId IS NULL OR a.userId = :userId) AND 
(:status IS NULL OR a.status = :status) AND 
(:minBudget IS NULL OR a.budget >= :minBudget) AND 
(:maxBudget IS NULL OR a.budget <= :maxBudget) AND 
a.isActive = true 
ORDER BY a.priority DESC, a.createdTime DESC

-- 查找预算使用率最高的广告
SELECT a FROM Advert a WHERE a.budget > 0 AND a.isActive = true 
ORDER BY (a.spentAmount / a.budget) DESC

-- 查找重复标题的广告
SELECT a.title, COUNT(a) FROM Advert a WHERE a.isActive = true 
GROUP BY a.title HAVING COUNT(a) > 1
```

### 统计相关查询
```sql
-- 按日期统计总展示和点击次数
SELECT s.statsDate, SUM(s.viewCount), SUM(s.clickCount), AVG(s.ctr) 
FROM AdvertStats s 
WHERE s.statsDate BETWEEN :startDate AND :endDate 
GROUP BY s.statsDate 
ORDER BY s.statsDate

-- 查找表现最好的广告
SELECT s.advertId, SUM(s.viewCount) as totalViews, SUM(s.clickCount) as totalClicks, 
AVG(s.ctr) as avgCtr 
FROM AdvertStats s 
WHERE s.statsDate BETWEEN :startDate AND :endDate 
GROUP BY s.advertId 
HAVING SUM(s.viewCount) > 0 
ORDER BY avgCtr DESC

-- 查找异常数据（点击率异常高的统计）
SELECT s FROM AdvertStats s WHERE s.ctr > :threshold AND s.viewCount > 0 
ORDER BY s.ctr DESC
```

## 📈 新增 API 接口

### 统计接口
- `GET /api/statistics/advert-status` - 广告状态统计
- `GET /api/statistics/daily-stats` - 每日统计数据
- `GET /api/statistics/dashboard` - 综合统计信息

### 用户管理接口
- `GET /api/users` - 获取所有用户
- `GET /api/users/{id}` - 根据ID获取用户
- `POST /api/users` - 创建用户
- `PUT /api/users/{id}` - 更新用户
- `DELETE /api/users/{id}` - 删除用户

### 分类管理接口
- `GET /api/categories` - 获取所有分类
- `GET /api/categories/tree` - 获取分类树
- `GET /api/categories/{id}/children` - 获取子分类
- `POST /api/categories` - 创建分类
- `PUT /api/categories/{id}` - 更新分类

## 🎯 业务场景

### 1. 用户权限管理
- 不同角色用户有不同的操作权限
- 管理员可以管理所有广告和用户
- 编辑可以创建和编辑广告
- 普通用户只能查看广告

### 2. 广告审核流程
- 草稿 → 待审核 → 已审核/已拒绝 → 投放中/暂停
- 支持批量审核和状态变更
- 审核记录和操作日志

### 3. 预算管理
- 设置广告预算和投放时间
- 实时监控预算使用情况
- 超预算预警和自动暂停

### 4. 数据统计分析
- 实时统计展示和点击数据
- 多维度数据分析和报表
- 异常数据检测和预警

### 5. 分类管理
- 支持多级分类管理
- 分类树形结构展示
- 分类下广告统计

## 🚀 部署和测试

### 1. 重新构建项目
```bash
mvn clean package -DskipTests
```

### 2. 重新部署 Docker 容器
```bash
docker-compose down
docker-compose up -d --build
```

### 3. 测试新功能
- 访问 `http://localhost:8080/api/statistics/dashboard` 查看统计信息
- 访问 `http://localhost:8080/api/adverts` 查看扩展后的广告数据
- 测试各种复杂的查询接口

## 📝 学习要点

### SQL 查询技巧
1. **聚合查询**: GROUP BY、HAVING、COUNT、SUM、AVG
2. **条件查询**: 多条件组合、NULL 值处理
3. **排序分页**: ORDER BY、LIMIT、OFFSET
4. **关联查询**: LEFT JOIN、INNER JOIN
5. **子查询**: 嵌套查询、EXISTS、IN
6. **窗口函数**: ROW_NUMBER、RANK、DENSE_RANK

### JPA 使用技巧
1. **方法命名查询**: findByXxx、countByXxx
2. **@Query 注解**: 自定义 SQL 和 JPQL
3. **分页排序**: Pageable、Sort
4. **关联映射**: @OneToMany、@ManyToOne
5. **事务管理**: @Transactional

### 业务设计模式
1. **分层架构**: Controller → Service → Repository
2. **数据验证**: 输入验证、业务规则验证
3. **异常处理**: 统一异常处理、错误码
4. **日志记录**: 操作日志、审计日志
5. **缓存策略**: 查询缓存、结果缓存

这个扩展版本提供了丰富的 SQL 练习场景，涵盖了增删改查、复杂查询、统计分析等各个方面，非常适合学习和实践！ 