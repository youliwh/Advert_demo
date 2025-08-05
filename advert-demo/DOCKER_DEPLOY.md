# Docker 部署指南

## 项目简介
这是一个基于 Spring Boot 的广告管理 Demo 项目，使用 PostgreSQL 数据库。

## 部署方式

### 方式一：使用 Docker Compose（推荐）

1. **确保已安装 Docker 和 Docker Compose**
   ```bash
   docker --version
   docker-compose --version
   ```

2. **一键部署**
   ```bash
   # 给部署脚本执行权限
   chmod +x deploy.sh
   
   # 运行部署脚本
   ./deploy.sh
   ```

3. **手动部署**
   ```bash
   # 构建并启动服务
   docker-compose up -d --build
   
   # 查看服务状态
   docker-compose ps
   
   # 查看日志
   docker-compose logs -f advert-app
   ```

### 方式二：单独构建镜像

1. **构建镜像**
   ```bash
   docker build -t advert-demo:latest .
   ```

2. **运行容器**
   ```bash
   # 先启动 PostgreSQL
   docker run -d --name postgres \
     -e POSTGRES_DB=advert_demo \
     -e POSTGRES_USER=postgres \
     -e POSTGRES_PASSWORD=wh200105104036 \
     -p 5432:5432 \
     postgres:13-alpine
   
   # 启动应用
   docker run -d --name advert-demo \
     -p 8080:8080 \
     -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/advert_demo \
     -e SPRING_DATASOURCE_USERNAME=postgres \
     -e SPRING_DATASOURCE_PASSWORD=wh200105104036 \
     advert-demo:latest
   ```

## 访问地址

- **应用地址**: http://localhost:8080
- **数据库端口**: 5432

## 常用命令

```bash
# 查看服务状态
docker-compose ps

# 查看应用日志
docker-compose logs -f advert-app

# 查看数据库日志
docker-compose logs -f postgres

# 停止服务
docker-compose down

# 重启服务
docker-compose restart

# 进入应用容器
docker-compose exec advert-app sh

# 进入数据库容器
docker-compose exec postgres psql -U postgres -d advert_demo
```

## 生产环境部署

对于生产环境，建议使用 `Dockerfile.prod`：

```bash
# 构建生产镜像
docker build -f Dockerfile.prod -t advert-demo:prod .

# 使用 docker-compose.prod.yml（需要创建）
docker-compose -f docker-compose.prod.yml up -d
```

## 注意事项

1. **数据持久化**: PostgreSQL 数据存储在 Docker volume 中，容器重启数据不会丢失
2. **网络配置**: 应用和数据库通过 Docker 网络通信
3. **环境变量**: 数据库连接信息通过环境变量配置，可根据需要修改
4. **端口映射**: 默认映射 8080 端口，可根据需要修改

## 故障排查

1. **应用无法启动**: 检查数据库是否正常启动
2. **数据库连接失败**: 检查环境变量配置
3. **端口冲突**: 修改 docker-compose.yml 中的端口映射

## 性能优化

1. **JVM 参数**: 可在 Dockerfile 中添加 JVM 参数
2. **数据库优化**: 可调整 PostgreSQL 配置
3. **资源限制**: 可在 docker-compose.yml 中添加资源限制 