-- 广告管理 Demo 初始化数据
-- 插入测试广告数据

INSERT INTO adverts (title, content, image_url, created_time, updated_time, is_active) VALUES
('春季促销活动', '春季新品上市，全场8折优惠！精选商品，限时抢购，机会难得，快来选购吧！', 'https://example.com/images/spring-sale.jpg', NOW(), NOW(), true),

('夏季清凉饮品', '炎炎夏日，来一杯冰镇饮品！我们提供各种口味的冷饮，包括果汁、奶茶、咖啡等，让您清凉一夏！', 'https://example.com/images/summer-drinks.jpg', NOW(), NOW(), true),

('秋季旅游推荐', '金秋时节，正是出游的好时候！我们为您推荐多个热门旅游目的地，包括山水风光、历史文化景点等，让您的秋季充满精彩！', 'https://example.com/images/autumn-travel.jpg', NOW(), NOW(), true),

('冬季保暖用品', '寒冬来袭，保暖必备！我们提供各种保暖用品，包括羽绒服、围巾、手套、暖宝宝等，让您温暖过冬！', 'https://example.com/images/winter-warm.jpg', NOW(), NOW(), true),

('新年特惠活动', '新年新气象，特惠活动开始啦！全场商品低至5折，更有精美礼品相送，快来抢购吧！', 'https://example.com/images/new-year-sale.jpg', NOW(), NOW(), true),

('电子产品推广', '最新电子产品上市！包括智能手机、平板电脑、笔记本电脑等，性能强劲，价格实惠，是您的不二选择！', 'https://example.com/images/electronics.jpg', NOW(), NOW(), true),

('美食推荐', '舌尖上的美味！我们为您推荐各种美食，包括中餐、西餐、日料、韩料等，让您品尝到世界各地的美味佳肴！', 'https://example.com/images/food-recommendation.jpg', NOW(), NOW(), true),

('健身器材推广', '健康生活，从运动开始！我们提供各种健身器材，包括跑步机、哑铃、瑜伽垫等，让您在家也能享受健身的乐趣！', 'https://example.com/images/fitness-equipment.jpg', NOW(), NOW(), true),

('图书推荐', '知识改变命运，阅读点亮人生！我们为您推荐各种好书，包括文学、科技、历史、哲学等各类图书，让您在阅读中成长！', 'https://example.com/images/book-recommendation.jpg', NOW(), NOW(), true),

('汽车保养服务', '爱车如命，保养先行！我们提供专业的汽车保养服务，包括机油更换、轮胎检查、空调清洗等，让您的爱车始终保持最佳状态！', 'https://example.com/images/car-maintenance.jpg', NOW(), NOW(), false); 