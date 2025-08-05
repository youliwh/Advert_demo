#!/bin/bash

echo "开始部署广告管理Demo项目..."

# 停止并删除现有容器
echo "停止现有容器..."
docker-compose down

# 构建并启动服务
echo "构建并启动服务..."
docker-compose up -d --build

# 等待服务启动
echo "等待服务启动..."
sleep 30

# 检查服务状态
echo "检查服务状态..."
docker-compose ps

echo "部署完成！"
echo "应用访问地址: http://localhost:8080"
echo "数据库端口: 5432"
echo ""
echo "查看日志命令:"
echo "  docker-compose logs -f advert-app"
echo "  docker-compose logs -f postgres"
echo ""
echo "停止服务命令:"
echo "  docker-compose down" 