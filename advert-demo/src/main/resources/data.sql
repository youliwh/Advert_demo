-- 逆变器产品管理 Demo 初始化数据
-- 插入测试广告数据

INSERT INTO adverts (title, content, image_url, created_time, updated_time, is_active, category_id, user_id, status, priority, start_time, end_time, budget, spent_amount, extended_properties, tags) VALUES
('微型逆变器新品上市', '新一代微型逆变器上市！采用先进MPPT技术，转换效率高达98.5%，支持1-4路MPPT，完美适配各种光伏组件。体积小巧，安装便捷，是分布式光伏系统的理想选择！', 'https://example.com/images/micro-inverter.jpg', NOW(), NOW(), true, 1, 1, 'ACTIVE', 10, NOW(), NOW() + INTERVAL '30 days', 10000.0, 2500.0, '{"power_rating": "1000W", "mppt_channels": 2, "efficiency": "98.5%", "warranty": "10年"}', '["微型逆变器", "MPPT", "高效"]'),

('组串式逆变器特惠', '大功率组串式逆变器限时特惠！功率范围3-110kW，支持多路MPPT，智能监控系统，适用于工商业光伏电站。现在购买享受8折优惠，更有专业安装服务！', 'https://example.com/images/string-inverter.jpg', NOW(), NOW(), true, 2, 1, 'ACTIVE', 8, NOW(), NOW() + INTERVAL '60 days', 8000.0, 1200.0, '{"power_rating": "50kW", "mppt_channels": 6, "efficiency": "99.0%", "application": "commercial"}', '["组串式", "工商业", "大功率"]'),

('储能逆变器解决方案', '全场景储能逆变器解决方案！支持并网/离网/混合模式，电池兼容性强，智能能量管理，为您的储能系统提供最佳性能。适用于家庭储能、工商业储能等多种场景！', 'https://example.com/images/energy-storage.jpg', NOW(), NOW(), true, 3, 2, 'APPROVED', 9, NOW(), NOW() + INTERVAL '45 days', 15000.0, 3000.0, '{"storage_capacity": "10kWh", "grid_mode": "hybrid", "battery_type": "lithium"}', '["储能", "混合模式", "智能管理"]'),

('集中式逆变器推广', '大型集中式逆变器，专为大型光伏电站设计！功率范围500kW-3MW，超高转换效率，智能故障诊断，远程监控功能完善。为您的电站提供稳定可靠的电力输出！', 'https://example.com/images/central-inverter.jpg', NOW(), NOW(), true, 1, 2, 'PENDING', 7, NOW(), NOW() + INTERVAL '90 days', 12000.0, 0.0, '{"power_rating": "1MW", "efficiency": "99.2%", "application": "utility_scale"}', '["集中式", "大功率", "电站级"]'),

('智能逆变器系统', '新一代智能逆变器系统！集成AI算法，智能MPPT追踪，实时数据分析，云平台监控。支持多种通信协议，为您的光伏系统提供智能化管理解决方案！', 'https://example.com/images/smart-inverter.jpg', NOW(), NOW(), true, 2, 1, 'ACTIVE', 10, NOW(), NOW() + INTERVAL '15 days', 20000.0, 8000.0, '{"ai_enabled": true, "cloud_monitoring": true, "communication": "RS485/WiFi/4G"}', '["智能", "AI", "云监控"]'),

('离网逆变器产品', '专业离网逆变器，为偏远地区提供稳定电力！纯正弦波输出，内置充电控制器，支持多种电池类型。适用于无电网地区、移动设备、应急电源等场景！', 'https://example.com/images/off-grid-inverter.jpg', NOW(), NOW(), true, 3, 3, 'ACTIVE', 6, NOW(), NOW() + INTERVAL '120 days', 3000.0, 1500.0, '{"output_type": "pure_sine", "battery_voltage": "48V", "application": "off_grid"}', '["离网", "纯正弦波", "应急电源"]'),

('微型逆变器安装服务', '专业微型逆变器安装服务！我们拥有经验丰富的技术团队，提供从设计、安装到调试的一站式服务。确保您的光伏系统安全可靠运行，享受最佳发电效率！', 'https://example.com/images/installation-service.jpg', NOW(), NOW(), true, 4, 2, 'APPROVED', 5, NOW(), NOW() + INTERVAL '75 days', 6000.0, 1000.0, '{"service_type": "installation", "warranty": "2年", "support": "24/7"}', '["安装服务", "技术支持", "售后保障"]'),

('逆变器维护保养', '逆变器定期维护保养服务！专业检测设备，预防性维护，延长设备寿命。我们提供年度维护计划，确保您的逆变器始终保持最佳工作状态！', 'https://example.com/images/maintenance-service.jpg', NOW(), NOW(), true, 5, 1, 'ACTIVE', 4, NOW(), NOW() + INTERVAL '60 days', 9000.0, 2000.0, '{"maintenance_type": "preventive", "frequency": "annual", "coverage": "full_system"}', '["维护保养", "预防性", "延长寿命"]'),

('逆变器技术培训', '逆变器技术培训课程！从基础原理到高级应用，涵盖各种类型逆变器的选型、安装、调试和维护。适合技术人员、安装商、系统集成商参加！', 'https://example.com/images/technical-training.jpg', NOW(), NOW(), true, 6, 3, 'ACTIVE', 3, NOW(), NOW() + INTERVAL '90 days', 5000.0, 500.0, '{"training_level": "advanced", "duration": "3天", "certification": true}', '["技术培训", "专业认证", "技能提升"]'),

('逆变器配件供应', '逆变器配件一站式供应！包括连接器、电缆、监控设备、通信模块等。原厂正品，质量保证，快速发货。为您的逆变器系统提供完整的配件支持！', 'https://example.com/images/accessories.jpg', NOW(), NOW(), false, 7, 2, 'REJECTED', 2, NOW(), NOW() + INTERVAL '30 days', 8000.0, 0.0, '{"parts_type": "genuine", "shipping": "fast", "warranty": "1年"}', '["配件", "原厂", "快速发货"]');

-- 插入用户数据
INSERT INTO users (username, password, email, real_name, phone, role, is_active, created_time, updated_time) VALUES
('admin', 'admin123', 'admin@inverter.com', '系统管理员', '13800138001', 'ADMIN', true, NOW(), NOW()),
('sales1', 'sales123', 'sales1@inverter.com', '销售经理王工', '13800138002', 'EDITOR', true, NOW(), NOW()),
('tech1', 'tech123', 'tech1@inverter.com', '技术工程师张工', '13800138003', 'USER', true, NOW(), NOW()),
('install1', 'install123', 'install1@inverter.com', '安装工程师李工', '13800138004', 'USER', true, NOW(), NOW()),
('support1', 'support123', 'support1@inverter.com', '技术支持刘工', '13800138005', 'EDITOR', true, NOW(), NOW());

-- 插入分类数据
INSERT INTO categories (name, description, parent_id, level, sort_order, is_active, created_time, updated_time) VALUES
('微型逆变器', '微型逆变器产品系列', NULL, 1, 1, true, NOW(), NOW()),
('组串式逆变器', '组串式逆变器产品系列', NULL, 1, 2, true, NOW(), NOW()),
('储能逆变器', '储能逆变器产品系列', NULL, 1, 3, true, NOW(), NOW()),
('集中式逆变器', '集中式逆变器产品系列', NULL, 1, 4, true, NOW(), NOW()),
('离网逆变器', '离网逆变器产品系列', NULL, 1, 5, true, NOW(), NOW()),
('智能逆变器', '智能逆变器产品系列', NULL, 1, 6, true, NOW(), NOW()),
('逆变器服务', '逆变器相关服务', NULL, 1, 7, true, NOW(), NOW()),
('单相微型逆变器', '单相微型逆变器', 1, 2, 1, true, NOW(), NOW()),
('三相微型逆变器', '三相微型逆变器', 1, 2, 2, true, NOW(), NOW()),
('小功率组串式', '小功率组串式逆变器', 2, 2, 1, true, NOW(), NOW()),
('大功率组串式', '大功率组串式逆变器', 2, 2, 2, true, NOW(), NOW()),
('并网储能', '并网储能逆变器', 3, 2, 1, true, NOW(), NOW()),
('离网储能', '离网储能逆变器', 3, 2, 2, true, NOW(), NOW()),
('工商业集中式', '工商业集中式逆变器', 4, 2, 1, true, NOW(), NOW()),
('电站级集中式', '电站级集中式逆变器', 4, 2, 2, true, NOW(), NOW()),
('纯离网逆变器', '纯离网逆变器', 5, 2, 1, true, NOW(), NOW()),
('混合离网逆变器', '混合离网逆变器', 5, 2, 2, true, NOW(), NOW()),
('AI智能逆变器', 'AI智能逆变器', 6, 2, 1, true, NOW(), NOW()),
('云监控逆变器', '云监控逆变器', 6, 2, 2, true, NOW(), NOW()),
('安装服务', '逆变器安装服务', 7, 2, 1, true, NOW(), NOW()),
('维护服务', '逆变器维护服务', 7, 2, 2, true, NOW(), NOW()),
('技术培训', '逆变器技术培训', 7, 2, 3, true, NOW(), NOW()),
('配件供应', '逆变器配件供应', 7, 2, 4, true, NOW(), NOW());

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