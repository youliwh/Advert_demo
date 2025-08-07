-- 广告管理 Demo 初始化数据
-- 插入测试广告数据

INSERT INTO adverts (title, content, image_url, created_time, updated_time, is_active, category_id, user_id, status, priority, start_time, end_time, budget, spent_amount) VALUES
('春季促销活动', '春季新品上市，全场8折优惠！精选商品，限时抢购，机会难得，快来选购吧！', 'https://example.com/images/spring-sale.jpg', NOW(), NOW(), true, 1, 1, 'ACTIVE', 10, NOW(), NOW() + INTERVAL '30 days', 1000.0, 250.0),

('夏季清凉饮品', '炎炎夏日，来一杯冰镇饮品！我们提供各种口味的冷饮，包括果汁、奶茶、咖啡等，让您清凉一夏！', 'https://example.com/images/summer-drinks.jpg', NOW(), NOW(), true, 2, 1, 'ACTIVE', 8, NOW(), NOW() + INTERVAL '60 days', 800.0, 120.0),

('秋季旅游推荐', '金秋时节，正是出游的好时候！我们为您推荐多个热门旅游目的地，包括山水风光、历史文化景点等，让您的秋季充满精彩！', 'https://example.com/images/autumn-travel.jpg', NOW(), NOW(), true, 3, 2, 'APPROVED', 9, NOW(), NOW() + INTERVAL '45 days', 1500.0, 300.0),

('冬季保暖用品', '寒冬来袭，保暖必备！我们提供各种保暖用品，包括羽绒服、围巾、手套、暖宝宝等，让您温暖过冬！', 'https://example.com/images/winter-warm.jpg', NOW(), NOW(), true, 1, 2, 'PENDING', 7, NOW(), NOW() + INTERVAL '90 days', 1200.0, 0.0),

('新年特惠活动', '新年新气象，特惠活动开始啦！全场商品低至5折，更有精美礼品相送，快来抢购吧！', 'https://example.com/images/new-year-sale.jpg', NOW(), NOW(), true, 2, 1, 'ACTIVE', 10, NOW(), NOW() + INTERVAL '15 days', 2000.0, 800.0),

('电子产品推广', '最新电子产品上市！包括智能手机、平板电脑、笔记本电脑等，性能强劲，价格实惠，是您的不二选择！', 'https://example.com/images/electronics.jpg', NOW(), NOW(), true, 3, 3, 'ACTIVE', 6, NOW(), NOW() + INTERVAL '120 days', 3000.0, 1500.0),

('美食推荐', '舌尖上的美味！我们为您推荐各种美食，包括中餐、西餐、日料、韩料等，让您品尝到世界各地的美味佳肴！', 'https://example.com/images/food-recommendation.jpg', NOW(), NOW(), true, 4, 2, 'APPROVED', 5, NOW(), NOW() + INTERVAL '75 days', 600.0, 100.0),

('健身器材推广', '健康生活，从运动开始！我们提供各种健身器材，包括跑步机、哑铃、瑜伽垫等，让您在家也能享受健身的乐趣！', 'https://example.com/images/fitness-equipment.jpg', NOW(), NOW(), true, 5, 1, 'ACTIVE', 4, NOW(), NOW() + INTERVAL '60 days', 900.0, 200.0),

('图书推荐', '知识改变命运，阅读点亮人生！我们为您推荐各种好书，包括文学、科技、历史、哲学等各类图书，让您在阅读中成长！', 'https://example.com/images/book-recommendation.jpg', NOW(), NOW(), true, 6, 3, 'ACTIVE', 3, NOW(), NOW() + INTERVAL '90 days', 500.0, 50.0),

('汽车保养服务', '爱车如命，保养先行！我们提供专业的汽车保养服务，包括机油更换、轮胎检查、空调清洗等，让您的爱车始终保持最佳状态！', 'https://example.com/images/car-maintenance.jpg', NOW(), NOW(), false, 7, 2, 'REJECTED', 2, NOW(), NOW() + INTERVAL '30 days', 800.0, 0.0);

-- 插入用户数据
INSERT INTO users (username, password, email, real_name, phone, role, is_active, created_time, updated_time) VALUES
('admin', 'admin123', 'admin@example.com', '系统管理员', '13800138001', 'ADMIN', true, NOW(), NOW()),
('editor1', 'editor123', 'editor1@example.com', '编辑小王', '13800138002', 'EDITOR', true, NOW(), NOW()),
('user1', 'user123', 'user1@example.com', '用户张三', '13800138003', 'USER', true, NOW(), NOW()),
('user2', 'user123', 'user2@example.com', '用户李四', '13800138004', 'USER', true, NOW(), NOW()),
('editor2', 'editor123', 'editor2@example.com', '编辑小李', '13800138005', 'EDITOR', true, NOW(), NOW());

-- 插入分类数据
INSERT INTO categories (name, description, parent_id, level, sort_order, is_active, created_time, updated_time) VALUES
('电商促销', '各类电商促销活动', NULL, 1, 1, true, NOW(), NOW()),
('生活服务', '日常生活相关服务', NULL, 1, 2, true, NOW(), NOW()),
('教育培训', '教育培训相关广告', NULL, 1, 3, true, NOW(), NOW()),
('数码科技', '数码科技产品推广', NULL, 1, 4, true, NOW(), NOW()),
('健康运动', '健康运动相关产品', NULL, 1, 5, true, NOW(), NOW()),
('文化娱乐', '文化娱乐相关广告', NULL, 1, 6, true, NOW(), NOW()),
('汽车服务', '汽车相关服务广告', NULL, 1, 7, true, NOW(), NOW()),
('服装配饰', '服装配饰类广告', 1, 2, 1, true, NOW(), NOW()),
('数码产品', '数码产品类广告', 1, 2, 2, true, NOW(), NOW()),
('家居用品', '家居用品类广告', 1, 2, 3, true, NOW(), NOW()),
('餐饮美食', '餐饮美食类广告', 2, 2, 1, true, NOW(), NOW()),
('美容美发', '美容美发类广告', 2, 2, 2, true, NOW(), NOW()),
('在线课程', '在线教育课程', 3, 2, 1, true, NOW(), NOW()),
('技能培训', '职业技能培训', 3, 2, 2, true, NOW(), NOW()),
('手机平板', '手机平板类产品', 4, 2, 1, true, NOW(), NOW()),
('电脑配件', '电脑配件类产品', 4, 2, 2, true, NOW(), NOW()),
('健身器材', '健身器材类产品', 5, 2, 1, true, NOW(), NOW()),
('运动服饰', '运动服饰类产品', 5, 2, 2, true, NOW(), NOW()),
('图书音像', '图书音像类产品', 6, 2, 1, true, NOW(), NOW()),
('游戏娱乐', '游戏娱乐类产品', 6, 2, 2, true, NOW(), NOW()),
('汽车保养', '汽车保养服务', 7, 2, 1, true, NOW(), NOW()),
('汽车销售', '汽车销售服务', 7, 2, 2, true, NOW(), NOW());

-- 插入广告统计数据
INSERT INTO advert_stats (advert_id, stats_date, view_count, click_count, ctr, created_time, updated_time) VALUES
(1, CURRENT_DATE, 1500, 75, 5.0, NOW(), NOW()),
(1, CURRENT_DATE - INTERVAL '1 day', 1200, 60, 5.0, NOW(), NOW()),
(1, CURRENT_DATE - INTERVAL '2 days', 1800, 90, 5.0, NOW(), NOW()),
(2, CURRENT_DATE, 800, 40, 5.0, NOW(), NOW()),
(2, CURRENT_DATE - INTERVAL '1 day', 600, 30, 5.0, NOW(), NOW()),
(3, CURRENT_DATE, 2000, 120, 6.0, NOW(), NOW()),
(3, CURRENT_DATE - INTERVAL '1 day', 1800, 108, 6.0, NOW(), NOW()),
(4, CURRENT_DATE, 500, 25, 5.0, NOW(), NOW()),
(5, CURRENT_DATE, 3000, 180, 6.0, NOW(), NOW()),
(5, CURRENT_DATE - INTERVAL '1 day', 2500, 150, 6.0, NOW(), NOW()),
(6, CURRENT_DATE, 1200, 72, 6.0, NOW(), NOW()),
(7, CURRENT_DATE, 900, 45, 5.0, NOW(), NOW()),
(8, CURRENT_DATE, 700, 35, 5.0, NOW(), NOW()),
(9, CURRENT_DATE, 400, 20, 5.0, NOW(), NOW()); 