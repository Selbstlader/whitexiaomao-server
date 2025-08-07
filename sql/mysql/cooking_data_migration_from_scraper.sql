-- ========================================
-- HowToCook 爬虫数据  ->  Yudao-Cooking 表结构迁移脚本
-- 创建时间：2024-xx-xx
-- ========================================

-- 0. 字符集设置
SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci;
SET character_set_client = utf8mb4;
SET character_set_connection = utf8mb4;
SET character_set_results = utf8mb4;

-- 1. 设置自增起始值（避免 ID 冲突，可自行调整）
ALTER TABLE `cooking_category`     AUTO_INCREMENT = 30000;
ALTER TABLE `cooking_dish`         AUTO_INCREMENT = 30000;
ALTER TABLE `cooking_ingredient`   AUTO_INCREMENT = 30000;
ALTER TABLE `cooking_step`         AUTO_INCREMENT = 30000;
ALTER TABLE `cooking_tip`          AUTO_INCREMENT = 30000;
ALTER TABLE `cooking_star_rating`  AUTO_INCREMENT = 30000;
ALTER TABLE `cooking_favorite`     AUTO_INCREMENT = 30000;

-- 2. 迁移菜品分类
INSERT INTO `cooking_category`(`name`,`description`,`sort`,`status`,`creator`,`create_time`,`tenant_id`)
SELECT 
  CONVERT(c.`name` USING utf8mb4) COLLATE utf8mb4_unicode_ci,
  CONCAT('爬虫导入的分类：',CONVERT(c.`name` USING utf8mb4)),
  c.`id`,
  0,
  'migration',
  NOW(),
  1
FROM `categories` c
WHERE NOT EXISTS (
  SELECT 1 FROM `cooking_category` cc
  WHERE cc.`name` COLLATE utf8mb4_unicode_ci = CONVERT(c.`name` USING utf8mb4) COLLATE utf8mb4_unicode_ci
    AND cc.`tenant_id` = 1
);

-- 3. 迁移菜品
INSERT INTO `cooking_dish`(
  `category_id`,`name`,`description`,`image_name`,`difficulty`,`cooking_time`,
  `servings`,`calories`,`tags`,`status`,`sort`,`view_count`,`like_count`,`creator`,`create_time`,`tenant_id`)
SELECT 
  cc.`id`,
  CONVERT(d.`name` USING utf8mb4) COLLATE utf8mb4_unicode_ci,
  COALESCE(CONVERT(d.`description` USING utf8mb4),''),
  COALESCE(d.`image_path`,''),
  COALESCE(d.`difficulty`,1),
  COALESCE(d.`cooking_time`,0),
  1                              -- 修改：旧表无 servings 字段，写入固定值
  ,0                             -- 修改：旧表无 calories 字段，写入固定值
  ,''                            -- 修改：旧表无 tags 字段，写入空串
  ,0,
  d.`id`,
  0,0,'migration',NOW(),1
FROM `dishes` d
JOIN `categories` c  ON d.`category_id` = c.`id`
JOIN `cooking_category` cc ON cc.`name` COLLATE utf8mb4_unicode_ci = CONVERT(c.`name` USING utf8mb4) COLLATE utf8mb4_unicode_ci AND cc.`tenant_id` = 1
WHERE NOT EXISTS (
  SELECT 1 FROM `cooking_dish` cd
  WHERE cd.`name` COLLATE utf8mb4_unicode_ci = CONVERT(d.`name` USING utf8mb4) COLLATE utf8mb4_unicode_ci
    AND cd.`category_id`=cc.`id` AND cd.`tenant_id`=1
);

-- 4. 迁移配料
INSERT INTO `cooking_ingredient`(`dish_id`,`name`,`amount`,`unit`,`is_optional`,`sort`,`remark`,`creator`,`create_time`,`tenant_id`)
SELECT 
  cd.`id`,
  CONVERT(i.`name` USING utf8mb4) COLLATE utf8mb4_unicode_ci,
  COALESCE(i.`amount`,''),
  LEFT(COALESCE(i.`unit`,''),50),  -- 调整：截断至 50 字符，避免超长
  COALESCE(i.`is_optional`,0),
  i.`id`,
  '',
  'migration',
  NOW(),
  1
FROM `ingredients` i
JOIN `dishes` d         ON i.`dish_id` = d.`id`
JOIN `categories` c      ON d.`category_id` = c.`id`
JOIN `cooking_category` cc ON cc.`name` COLLATE utf8mb4_unicode_ci = CONVERT(c.`name` USING utf8mb4) COLLATE utf8mb4_unicode_ci AND cc.`tenant_id`=1
JOIN `cooking_dish` cd     ON cd.`name` COLLATE utf8mb4_unicode_ci = CONVERT(d.`name` USING utf8mb4) COLLATE utf8mb4_unicode_ci AND cd.`category_id`=cc.`id` AND cd.`tenant_id`=1;

-- 5. 迁移步骤
INSERT INTO `cooking_step`(`dish_id`,`step_number`,`title`,`description`,`image_url`,`time_required`,`temperature`,`tips`,`creator`,`create_time`,`tenant_id`)
SELECT 
  cd.`id`,
  s.`step_number`,
  '',
  CONVERT(s.`description` USING utf8mb4) COLLATE utf8mb4_unicode_ci,
  COALESCE(s.`image_path`,''),     -- 修改：旧表字段为 image_path
  0 ,                              -- 修改：旧表无 time_required 字段
  '' ,                             -- 修改：旧表无 temperature 字段
  '' ,                             -- 修改：旧表无 tips 字段
  'migration',
  NOW(),
  1
FROM `steps` s
JOIN `dishes` d ON s.`dish_id` = d.`id`
JOIN `categories` c ON d.`category_id` = c.`id`
JOIN `cooking_category` cc ON cc.`name` COLLATE utf8mb4_unicode_ci = CONVERT(c.`name` USING utf8mb4) COLLATE utf8mb4_unicode_ci AND cc.`tenant_id`=1
JOIN `cooking_dish` cd ON cd.`name` COLLATE utf8mb4_unicode_ci = CONVERT(d.`name` USING utf8mb4) COLLATE utf8mb4_unicode_ci AND cd.`category_id`=cc.`id` AND cd.`tenant_id`=1;

-- 6. 迁移小贴士
INSERT INTO `cooking_tip`(`dish_id`,`type`,`title`,`content`,`sort`,`creator`,`create_time`,`tenant_id`)
SELECT 
  cd.`id`,
  1,
  '',
  CONVERT(t.`content` USING utf8mb4) COLLATE utf8mb4_unicode_ci,
  t.`id`,
  'migration',
  NOW(),
  1
FROM `tips` t
JOIN `dishes` d ON t.`dish_id` = d.`id`
JOIN `categories` c ON d.`category_id` = c.`id`
JOIN `cooking_category` cc ON cc.`name` COLLATE utf8mb4_unicode_ci = CONVERT(c.`name` USING utf8mb4) COLLATE utf8mb4_unicode_ci AND cc.`tenant_id`=1
JOIN `cooking_dish` cd ON cd.`name` COLLATE utf8mb4_unicode_ci = CONVERT(d.`name` USING utf8mb4) COLLATE utf8mb4_unicode_ci AND cd.`category_id`=cc.`id` AND cd.`tenant_id`=1;

-- 7. 迁移星级评分（若有）
INSERT INTO `cooking_star_rating`(`dish_id`,`user_id`,`star_level`,`comment`,`taste_score`,`difficulty_score`,`time_score`,`status`,`creator`,`create_time`,`tenant_id`)
SELECT 
  cd.`id`,
  1,                       -- 如果有真实用户 ID，请替换
  sr.`star_level`,
  '爬虫导入的评分',
  sr.`star_level`,
  sr.`star_level`,
  sr.`star_level`,
  1,
  'migration',
  NOW(),
  1
FROM `star_ratings` sr
JOIN `dishes` d ON sr.`dish_id` = d.`id`
JOIN `categories` c ON d.`category_id` = c.`id`
JOIN `cooking_category` cc ON cc.`name` COLLATE utf8mb4_unicode_ci = CONVERT(c.`name` USING utf8mb4) COLLATE utf8mb4_unicode_ci AND cc.`tenant_id`=1
JOIN `cooking_dish` cd ON cd.`name` COLLATE utf8mb4_unicode_ci = CONVERT(d.`name` USING utf8mb4) COLLATE utf8mb4_unicode_ci AND cd.`category_id`=cc.`id` AND cd.`tenant_id`=1;

-- 8. 迁移完成验证
SELECT '分类', COUNT(*) FROM `cooking_category` WHERE `creator`='migration'
UNION ALL
SELECT '菜品', COUNT(*) FROM `cooking_dish` WHERE `creator`='migration'
UNION ALL
SELECT '配料', COUNT(*) FROM `cooking_ingredient` WHERE `creator`='migration'
UNION ALL
SELECT '步骤', COUNT(*) FROM `cooking_step` WHERE `creator`='migration'
UNION ALL
SELECT '小贴士', COUNT(*) FROM `cooking_tip` WHERE `creator`='migration'
UNION ALL
SELECT '评分', COUNT(*) FROM `cooking_star_rating` WHERE `creator`='migration';
-- ========================================