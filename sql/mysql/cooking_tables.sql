-- ========================================
-- 烹饪模块数据库表结构
-- 创建时间：2024-12-19
-- 说明：为 Cooking 烹饪管理模块创建数据库表结构
-- ========================================

-- ========================================
-- 菜品分类表 (cooking_category)
-- ========================================
DROP TABLE IF EXISTS `cooking_category`;
CREATE TABLE `cooking_category` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类编号',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '分类名称',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '分类描述',
  `sort` int NOT NULL DEFAULT 0 COMMENT '显示顺序',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`) USING BTREE,
  KEY `idx_status` (`status`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜品分类表';

-- ========================================
-- 菜品表 (cooking_dish)
-- ========================================
DROP TABLE IF EXISTS `cooking_dish`;
CREATE TABLE `cooking_dish` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜品编号',
  `category_id` bigint NOT NULL COMMENT '分类ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '菜品名称',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '菜品描述',
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '菜品图片URL',
  `image_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '菜品图片名称',
  `difficulty` tinyint NOT NULL DEFAULT 1 COMMENT '难度等级（1简单 2中等 3困难）',
  `cooking_time` int NOT NULL DEFAULT 0 COMMENT '烹饪时间（分钟）',
  `servings` int NOT NULL DEFAULT 1 COMMENT '份量（人数）',
  `calories` int DEFAULT 0 COMMENT '卡路里',
  `tags` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '标签（用逗号分隔）',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `sort` int NOT NULL DEFAULT 0 COMMENT '显示顺序',
  `view_count` int NOT NULL DEFAULT 0 COMMENT '浏览次数',
  `like_count` int NOT NULL DEFAULT 0 COMMENT '点赞次数',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_category_id` (`category_id`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`) USING BTREE,
  KEY `idx_status` (`status`) USING BTREE,
  KEY `idx_difficulty` (`difficulty`) USING BTREE,
  KEY `idx_cooking_time` (`cooking_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜品表';

-- ========================================
-- 配料表 (cooking_ingredient)
-- ========================================
DROP TABLE IF EXISTS `cooking_ingredient`;
CREATE TABLE `cooking_ingredient` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '配料编号',
  `dish_id` bigint NOT NULL COMMENT '菜品ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '配料名称',
  `amount` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '配料份量',
  `unit` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '配料单位',
  `is_optional` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否可选（0必需 1可选）',
  `sort` int NOT NULL DEFAULT 0 COMMENT '显示顺序',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '备注',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_dish_id` (`dish_id`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`) USING BTREE,
  KEY `idx_sort` (`sort`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='配料表';

-- ========================================
-- 烹饪步骤表 (cooking_step)
-- ========================================
DROP TABLE IF EXISTS `cooking_step`;
CREATE TABLE `cooking_step` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '步骤编号',
  `dish_id` bigint NOT NULL COMMENT '菜品ID',
  `step_number` int NOT NULL COMMENT '步骤序号',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '步骤标题',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '步骤描述',
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '步骤图片URL',
  `time_required` int DEFAULT 0 COMMENT '所需时间（分钟）',
  `temperature` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '温度要求',
  `tips` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '小贴士',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_dish_id` (`dish_id`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`) USING BTREE,
  KEY `idx_step_number` (`step_number`) USING BTREE,
  UNIQUE KEY `uk_dish_step` (`dish_id`, `step_number`, `tenant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='烹饪步骤表';

-- ========================================
-- 烹饪小贴士表 (cooking_tip)
-- ========================================
DROP TABLE IF EXISTS `cooking_tip`;
CREATE TABLE `cooking_tip` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '小贴士编号',
  `dish_id` bigint NOT NULL COMMENT '菜品ID',
  `type` tinyint NOT NULL DEFAULT 1 COMMENT '类型（1技巧 2注意事项 3营养价值）',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '小贴士内容',
  `sort` int NOT NULL DEFAULT 0 COMMENT '显示顺序',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_dish_id` (`dish_id`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`) USING BTREE,
  KEY `idx_type` (`type`) USING BTREE,
  KEY `idx_sort` (`sort`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='烹饪小贴士表';

-- ========================================
-- 星级评分表 (cooking_star_rating)
-- ========================================
DROP TABLE IF EXISTS `cooking_star_rating`;
CREATE TABLE `cooking_star_rating` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '评分编号',
  `dish_id` bigint NOT NULL COMMENT '菜品ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `star_level` tinyint NOT NULL DEFAULT 5 COMMENT '星级评分（1-5星）',
  `comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '评论内容',
  `taste_score` tinyint DEFAULT 5 COMMENT '口味评分（1-5分）',
  `difficulty_score` tinyint DEFAULT 5 COMMENT '难度评分（1-5分）',
  `time_score` tinyint DEFAULT 5 COMMENT '时间评分（1-5分）',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态（0待审核 1已通过 2已拒绝）',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_dish_id` (`dish_id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`) USING BTREE,
  KEY `idx_star_level` (`star_level`) USING BTREE,
  KEY `idx_status` (`status`) USING BTREE,
  UNIQUE KEY `uk_dish_user` (`dish_id`, `user_id`, `tenant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='星级评分表';

-- ========================================
-- 菜品收藏表 (cooking_favorite)
-- ========================================
DROP TABLE IF EXISTS `cooking_favorite`;
CREATE TABLE `cooking_favorite` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '收藏编号',
  `dish_id` bigint NOT NULL COMMENT '菜品ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_dish_id` (`dish_id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`) USING BTREE,
  UNIQUE KEY `uk_dish_user` (`dish_id`, `user_id`, `tenant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜品收藏表';

-- ========================================
-- 初始化数据
-- ========================================

-- 插入默认菜品分类
INSERT INTO `cooking_category` (`id`, `name`, `description`, `sort`, `status`, `creator`, `tenant_id`) VALUES
(1, '家常菜', '日常家庭烹饪的经典菜品', 1, 0, 'system', 1),
(2, '川菜', '四川地方特色菜系', 2, 0, 'system', 1),
(3, '粤菜', '广东地方特色菜系', 3, 0, 'system', 1),
(4, '鲁菜', '山东地方特色菜系', 4, 0, 'system', 1),
(5, '苏菜', '江苏地方特色菜系', 5, 0, 'system', 1),
(6, '浙菜', '浙江地方特色菜系', 6, 0, 'system', 1),
(7, '闽菜', '福建地方特色菜系', 7, 0, 'system', 1),
(8, '湘菜', '湖南地方特色菜系', 8, 0, 'system', 1),
(9, '汤品', '各类汤品和煲汤', 9, 0, 'system', 1),
(10, '甜品', '各类甜品和点心', 10, 0, 'system', 1),
(11, '素食', '素食和蔬菜类菜品', 11, 0, 'system', 1),
(12, '海鲜', '海鲜类菜品', 12, 0, 'system', 1);

-- ========================================
-- 外键约束（可选）
-- ========================================
-- 注意：在生产环境中，可以根据需要添加外键约束
-- ALTER TABLE `cooking_dish` ADD CONSTRAINT `fk_dish_category` FOREIGN KEY (`category_id`) REFERENCES `cooking_category` (`id`);
-- ALTER TABLE `cooking_ingredient` ADD CONSTRAINT `fk_ingredient_dish` FOREIGN KEY (`dish_id`) REFERENCES `cooking_dish` (`id`);
-- ALTER TABLE `cooking_step` ADD CONSTRAINT `fk_step_dish` FOREIGN KEY (`dish_id`) REFERENCES `cooking_dish` (`id`);
-- ALTER TABLE `cooking_tip` ADD CONSTRAINT `fk_tip_dish` FOREIGN KEY (`dish_id`) REFERENCES `cooking_dish` (`id`);
-- ALTER TABLE `cooking_star_rating` ADD CONSTRAINT `fk_rating_dish` FOREIGN KEY (`dish_id`) REFERENCES `cooking_dish` (`id`);
-- ALTER TABLE `cooking_favorite` ADD CONSTRAINT `fk_favorite_dish` FOREIGN KEY (`dish_id`) REFERENCES `cooking_dish` (`id`);

-- ========================================
-- 执行完成提示
-- ========================================
-- 烹饪模块数据库表创建完成！
-- 包含以下表：
-- 1. cooking_category - 菜品分类表
-- 2. cooking_dish - 菜品表
-- 3. cooking_ingredient - 配料表
-- 4. cooking_step - 烹饪步骤表
-- 5. cooking_tip - 烹饪小贴士表
-- 6. cooking_star_rating - 星级评分表
-- 7. cooking_favorite - 菜品收藏表
-- 
-- 所有表都遵循yudao项目的设计规范：
-- - 包含租户ID (tenant_id)
-- - 包含逻辑删除标记 (deleted)
-- - 包含创建者和更新者信息
-- - 包含创建时间和更新时间
-- - 使用utf8mb4字符集
-- ========================================

-- ========================================
-- 数据库表结构更新语句
-- ========================================

-- 为 cooking_dish 表添加 image_name 字段（如果表已存在）
ALTER TABLE `cooking_dish` ADD COLUMN `image_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '菜品图片名称' AFTER `image_url`;

-- ========================================