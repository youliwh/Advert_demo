# API测试脚本
Write-Host "=== 测试新添加的API接口 ===" -ForegroundColor Green

# 测试用户API
Write-Host "`n1. 测试用户管理API:" -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/api/users" -Method GET
    Write-Host "   GET /api/users - 状态码: $($response.StatusCode)" -ForegroundColor Green
    Write-Host "   返回用户数量: $($response.Content | ConvertFrom-Json | Measure-Object | Select-Object -ExpandProperty Count)"
} catch {
    Write-Host "   GET /api/users - 错误: $($_.Exception.Message)" -ForegroundColor Red
}

# 测试用户统计API
Write-Host "`n2. 测试用户统计API:" -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/api/users/statistics" -Method GET
    Write-Host "   GET /api/users/statistics - 状态码: $($response.StatusCode)" -ForegroundColor Green
    $stats = $response.Content | ConvertFrom-Json
    Write-Host "   总用户数: $($stats.totalUsers), 激活用户数: $($stats.activeUsers)"
} catch {
    Write-Host "   GET /api/users/statistics - 错误: $($_.Exception.Message)" -ForegroundColor Red
}

# 测试分类API
Write-Host "`n3. 测试分类管理API:" -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/api/categories" -Method GET
    Write-Host "   GET /api/categories - 状态码: $($response.StatusCode)" -ForegroundColor Green
    Write-Host "   返回分类数量: $($response.Content | ConvertFrom-Json | Measure-Object | Select-Object -ExpandProperty Count)"
} catch {
    Write-Host "   GET /api/categories - 错误: $($_.Exception.Message)" -ForegroundColor Red
}

# 测试分类统计API
Write-Host "`n4. 测试分类统计API:" -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/api/categories/statistics" -Method GET
    Write-Host "   GET /api/categories/statistics - 状态码: $($response.StatusCode)" -ForegroundColor Green
} catch {
    Write-Host "   GET /api/categories/statistics - 错误: $($_.Exception.Message)" -ForegroundColor Red
}

# 测试广告统计API
Write-Host "`n5. 测试广告统计API:" -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/api/advert-stats" -Method GET
    Write-Host "   GET /api/advert-stats - 状态码: $($response.StatusCode)" -ForegroundColor Green
    Write-Host "   返回统计数量: $($response.Content | ConvertFrom-Json | Measure-Object | Select-Object -ExpandProperty Count)"
} catch {
    Write-Host "   GET /api/advert-stats - 错误: $($_.Exception.Message)" -ForegroundColor Red
}

# 测试广告统计摘要API
Write-Host "`n6. 测试广告统计摘要API:" -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/api/advert-stats/summary" -Method GET
    Write-Host "   GET /api/advert-stats/summary - 状态码: $($response.StatusCode)" -ForegroundColor Green
} catch {
    Write-Host "   GET /api/advert-stats/summary - 错误: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "`n=== API测试完成 ===" -ForegroundColor Green 