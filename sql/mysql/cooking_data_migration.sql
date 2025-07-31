-- ========================================
-- HowToCook 数据迁移脚本
-- 创建时间：2024-12-19
-- 说明：将原有的 HowToCook 数据库表迁移到新的 cooking 表结构
-- ========================================

-- ========================================
-- 数据迁移前的准备工作
-- ========================================

-- 1. 确保目标表已创建（执行 cooking_tables.sql）
-- 2. 确保源数据库表存在且有数据
-- 3. 建议在迁移前备份数据
-- 4. 设置字符集以避免冲突
SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci;
SET character_set_client = utf8mb4;
SET character_set_connection = utf8mb4;
SET character_set_results = utf8mb4;

-- 设置自增ID起始值
ALTER TABLE `cooking_category` AUTO_INCREMENT = 30000;
ALTER TABLE `cooking_dish` AUTO_INCREMENT = 30000;
ALTER TABLE `cooking_ingredient` AUTO_INCREMENT = 30000;
ALTER TABLE `cooking_step` AUTO_INCREMENT = 30000;
ALTER TABLE `cooking_tip` AUTO_INCREMENT = 30000;
ALTER TABLE `cooking_star_rating` AUTO_INCREMENT = 30000;
ALTER TABLE `cooking_favorite` AUTO_INCREMENT = 30000;

-- ========================================
-- 1. 迁移菜品分类数据 (categories -> cooking_category)
-- ========================================

INSERT INTO `cooking_category` (
    `name`, 
    `description`, 
    `sort`, 
    `status`, 
    `creator`, 
    `create_time`, 
    `tenant_id`
)
SELECT 
    CONVERT(c.`name` USING utf8mb4) COLLATE utf8mb4_unicode_ci AS `name`,
    CONCAT('从 HowToCook 导入的分类：', CONVERT(c.`name` USING utf8mb4)) AS `description`,
    c.`id` AS `sort`,
    0 AS `status`,
    'migration' AS `creator`,
    NOW() AS `create_time`,
    1 AS `tenant_id`
FROM `categories` c
WHERE NOT EXISTS (
    SELECT 1 FROM `cooking_category` cc 
    WHERE cc.`name` COLLATE utf8mb4_unicode_ci = CONVERT(c.`name` USING utf8mb4) COLLATE utf8mb4_unicode_ci AND cc.`tenant_id` = 1
);

-- ========================================
-- 2. 迁移菜品数据 (dishes -> cooking_dish)
-- ========================================

INSERT INTO `cooking_dish` (
    `category_id`,
    `name`,
    `description`,
    `image_url`,
    `image_name`,
    `difficulty`,
    `cooking_time`,
    `servings`,
    `calories`,
    `tags`,
    `status`,
    `sort`,
    `view_count`,
    `like_count`,
    `creator`,
    `create_time`,
    `tenant_id`
)
SELECT 
    cc.`id` AS `category_id`,
    CONVERT(d.`name` USING utf8mb4) COLLATE utf8mb4_unicode_ci AS `name`,
    COALESCE(CONVERT(d.`description` USING utf8mb4), '') AS `description`,
    '' AS `image_url`,  -- 原表存储的是 LONGBLOB，这里暂时置空
    COALESCE(CONVERT(d.`image_name` USING utf8mb4), '') AS `image_name`,
    COALESCE(d.`difficulty`, 1) AS `difficulty`,
    0 AS `cooking_time`,  -- 原表没有此字段，默认为0
    1 AS `servings`,      -- 原表没有此字段，默认为1
    0 AS `calories`,      -- 原表没有此字段，默认为0
    '' AS `tags`,         -- 原表没有此字段，默认为空
    0 AS `status`,
    d.`id` AS `sort`,
    0 AS `view_count`,
    0 AS `like_count`,
    'migration' AS `creator`,
    NOW() AS `create_time`,
    1 AS `tenant_id`
FROM `dishes` d
INNER JOIN `categories` c ON d.`category_id` = c.`id`
INNER JOIN `cooking_category` cc ON cc.`name` COLLATE utf8mb4_unicode_ci = CONVERT(c.`name` USING utf8mb4) COLLATE utf8mb4_unicode_ci AND cc.`tenant_id` = 1
WHERE NOT EXISTS (
    SELECT 1 FROM `cooking_dish` cd 
    WHERE cd.`name` COLLATE utf8mb4_unicode_ci = CONVERT(d.`name` USING utf8mb4) COLLATE utf8mb4_unicode_ci AND cd.`category_id` = cc.`id` AND cd.`tenant_id` = 1
);

-- ========================================
-- 3. 迁移配料数据 (ingredients -> cooking_ingredient)
-- ========================================

INSERT INTO `cooking_ingredient` (
    `dish_id`,
    `name`,
    `amount`,
    `unit`,
    `is_optional`,
    `sort`,
    `remark`,
    `creator`,
    `create_time`,
    `tenant_id`
)
SELECT 
    cd.`id` AS `dish_id`,
    CONVERT(i.`name` USING utf8mb4) COLLATE utf8mb4_unicode_ci AS `name`,
    COALESCE(CONVERT(i.`amount` USING utf8mb4), '') AS `amount`,
    COALESCE(CONVERT(i.`unit` USING utf8mb4), '') AS `unit`,
    COALESCE(i.`is_optional`, 0) AS `is_optional`,
    i.`id` AS `sort`,
    '' AS `remark`,
    'migration' AS `creator`,
    NOW() AS `create_time`,
    1 AS `tenant_id`
FROM `ingredients` i
INNER JOIN `dishes` d ON i.`dish_id` = d.`id`
INNER JOIN `categories` c ON d.`category_id` = c.`id`
INNER JOIN `cooking_category` cc ON cc.`name` COLLATE utf8mb4_unicode_ci = CONVERT(c.`name` USING utf8mb4) COLLATE utf8mb4_unicode_ci AND cc.`tenant_id` = 1
INNER JOIN `cooking_dish` cd ON cd.`name` COLLATE utf8mb4_unicode_ci = CONVERT(d.`name` USING utf8mb4) COLLATE utf8mb4_unicode_ci AND cd.`category_id` = cc.`id` AND cd.`tenant_id` = 1;

-- ========================================
-- 4. 迁移烹饪步骤数据 (steps -> cooking_step)
-- ========================================

INSERT INTO `cooking_step` (
    `dish_id`,
    `step_number`,
    `title`,
    `description`,
    `image_url`,
    `time_required`,
    `temperature`,
    `tips`,
    `creator`,
    `create_time`,
    `tenant_id`
)
SELECT 
    cd.`id` AS `dish_id`,
    s.`step_number`,
    '' AS `title`,  -- 原表没有标题字段
    CONVERT(s.`description` USING utf8mb4) COLLATE utf8mb4_unicode_ci AS `description`,
    '' AS `image_url`,  -- 原表存储的是 LONGBLOB，这里暂时置空
    0 AS `time_required`,  -- 原表没有此字段
    '' AS `temperature`,   -- 原表没有此字段
    '' AS `tips`,          -- 原表没有此字段
    'migration' AS `creator`,
    NOW() AS `create_time`,
    1 AS `tenant_id`
FROM `steps` s
INNER JOIN `dishes` d ON s.`dish_id` = d.`id`
INNER JOIN `categories` c ON d.`category_id` = c.`id`
INNER JOIN `cooking_category` cc ON cc.`name` COLLATE utf8mb4_unicode_ci = CONVERT(c.`name` USING utf8mb4) COLLATE utf8mb4_unicode_ci AND cc.`tenant_id` = 1
INNER JOIN `cooking_dish` cd ON cd.`name` COLLATE utf8mb4_unicode_ci = CONVERT(d.`name` USING utf8mb4) COLLATE utf8mb4_unicode_ci AND cd.`category_id` = cc.`id` AND cd.`tenant_id` = 1;

-- ========================================
-- 5. 迁移烹饪小贴士数据 (tips -> cooking_tip)
-- ========================================

INSERT INTO `cooking_tip` (
    `dish_id`,
    `type`,
    `title`,
    `content`,
    `sort`,
    `creator`,
    `create_time`,
    `tenant_id`
)
SELECT 
    cd.`id` AS `dish_id`,
    1 AS `type`,  -- 默认类型为技巧
    '' AS `title`,
    CONVERT(t.`content` USING utf8mb4) COLLATE utf8mb4_unicode_ci AS `content`,
    t.`id` AS `sort`,
    'migration' AS `creator`,
    NOW() AS `create_time`,
    1 AS `tenant_id`
FROM `tips` t
INNER JOIN `dishes` d ON t.`dish_id` = d.`id`
INNER JOIN `categories` c ON d.`category_id` = c.`id`
INNER JOIN `cooking_category` cc ON cc.`name` COLLATE utf8mb4_unicode_ci = CONVERT(c.`name` USING utf8mb4) COLLATE utf8mb4_unicode_ci AND cc.`tenant_id` = 1
INNER JOIN `cooking_dish` cd ON cd.`name` COLLATE utf8mb4_unicode_ci = CONVERT(d.`name` USING utf8mb4) COLLATE utf8mb4_unicode_ci AND cd.`category_id` = cc.`id` AND cd.`tenant_id` = 1;

-- ========================================
-- 6. 迁移星级评分数据 (star_ratings -> cooking_star_rating)
-- ========================================

-- 注意：原表的星级评分没有用户ID，这里使用默认用户ID = 1
INSERT INTO `cooking_star_rating` (
    `dish_id`,
    `user_id`,
    `star_level`,
    `comment`,
    `taste_score`,
    `difficulty_score`,
    `time_score`,
    `status`,
    `creator`,
    `create_time`,
    `tenant_id`
)
SELECT 
    cd.`id` AS `dish_id`,
    1 AS `user_id`,  -- 默认用户ID，实际使用时需要调整
    sr.`star_level`,
    '从 HowToCook 迁移的评分' AS `comment`,
    sr.`star_level` AS `taste_score`,
    sr.`star_level` AS `difficulty_score`,
    sr.`star_level` AS `time_score`,
    1 AS `status`,  -- 默认已通过
    'migration' AS `creator`,
    NOW() AS `create_time`,
    1 AS `tenant_id`
FROM `star_ratings` sr
INNER JOIN `dishes` d ON sr.`dish_id` = d.`id`
INNER JOIN `categories` c ON d.`category_id` = c.`id`
INNER JOIN `cooking_category` cc ON cc.`name` COLLATE utf8mb4_unicode_ci = CONVERT(c.`name` USING utf8mb4) COLLATE utf8mb4_unicode_ci AND cc.`tenant_id` = 1
INNER JOIN `cooking_dish` cd ON cd.`name` COLLATE utf8mb4_unicode_ci = CONVERT(d.`name` USING utf8mb4) COLLATE utf8mb4_unicode_ci AND cd.`category_id` = cc.`id` AND cd.`tenant_id` = 1;

-- ========================================
-- 数据迁移后的验证查询
-- ========================================

-- 验证迁移结果
SELECT '分类迁移结果' AS 检查项, COUNT(*) AS 记录数 FROM `cooking_category` WHERE `creator` = 'migration'
UNION ALL
SELECT '菜品迁移结果' AS 检查项, COUNT(*) AS 记录数 FROM `cooking_dish` WHERE `creator` = 'migration'
UNION ALL
SELECT '配料迁移结果' AS 检查项, COUNT(*) AS 记录数 FROM `cooking_ingredient` WHERE `creator` = 'migration'
UNION ALL
SELECT '步骤迁移结果' AS 检查项, COUNT(*) AS 记录数 FROM `cooking_step` WHERE `creator` = 'migration'
UNION ALL
SELECT '小贴士迁移结果' AS 检查项, COUNT(*) AS 记录数 FROM `cooking_tip` WHERE `creator` = 'migration'
UNION ALL
SELECT '评分迁移结果' AS 检查项, COUNT(*) AS 记录数 FROM `cooking_star_rating` WHERE `creator` = 'migration';

-- ========================================
-- 迁移完成说明
-- ========================================
-- 
-- 数据迁移完成！
-- 
-- 迁移内容包括：
-- 1. categories -> cooking_category (菜品分类)
-- 2. dishes -> cooking_dish (菜品信息)
-- 3. ingredients -> cooking_ingredient (配料信息)
-- 4. steps -> cooking_step (烹饪步骤)
-- 5. tips -> cooking_tip (烹饪小贴士)
-- 6. star_ratings -> cooking_star_rating (星级评分)
-- 
-- 注意事项：
-- 1. 图片数据（LONGBLOB）未迁移，需要单独处理
-- 2. 星级评分的用户ID使用了默认值1，需要根据实际情况调整
-- 3. 新表中的一些字段（如烹饪时间、卡路里等）使用了默认值
-- 4. 所有迁移的数据都标记了 creator = 'migration' 便于识别
-- 5. 使用了 tenant_id = 1，多租户环境下需要调整
-- 
-- ========================================