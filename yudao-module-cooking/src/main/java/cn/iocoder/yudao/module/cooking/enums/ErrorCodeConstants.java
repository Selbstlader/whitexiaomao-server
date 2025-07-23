package cn.iocoder.yudao.module.cooking.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * 烹飪模組錯誤碼常量
 *
 * error code 範圍：1-007-000-000 ~ 1-007-999-999
 */
public interface ErrorCodeConstants {

    // ========== 菜品分類 1-007-000-000 ==========
    ErrorCode CATEGORY_NOT_EXISTS = new ErrorCode(1_007_000_001, "菜品分類不存在");
    ErrorCode CATEGORY_NAME_DUPLICATE = new ErrorCode(1_007_000_002, "菜品分類名稱已存在");
    ErrorCode CATEGORY_HAS_DISHES = new ErrorCode(1_007_000_003, "菜品分類下已有菜品，無法刪除");

    // ========== 菜品 1-007-001-000 ==========
    ErrorCode DISH_NOT_EXISTS = new ErrorCode(1_007_001_001, "菜品不存在");
    ErrorCode DISH_NAME_DUPLICATE = new ErrorCode(1_007_001_002, "菜品名稱已存在");
    ErrorCode DISH_IMAGE_UPLOAD_FAILED = new ErrorCode(1_007_001_003, "菜品圖片上傳失敗");

    // ========== 配料 1-007-002-000 ==========
    ErrorCode INGREDIENT_NOT_EXISTS = new ErrorCode(1_007_002_001, "配料不存在");
    ErrorCode INGREDIENT_NAME_DUPLICATE = new ErrorCode(1_007_002_002, "配料名稱已存在");
    ErrorCode INGREDIENT_DISH_NOT_MATCH = new ErrorCode(1_007_002_003, "配料與菜品不匹配");

    // ========== 步驟 1-007-003-000 ==========
    ErrorCode STEP_NOT_EXISTS = new ErrorCode(1_007_003_001, "步驟不存在");
    ErrorCode STEP_NUMBER_DUPLICATE = new ErrorCode(1_007_003_002, "步驟順序已存在");
    ErrorCode STEP_DISH_NOT_MATCH = new ErrorCode(1_007_003_003, "步驟與菜品不匹配");
    ErrorCode STEP_IMAGE_UPLOAD_FAILED = new ErrorCode(1_007_003_004, "步驟圖片上傳失敗");

    // ========== 小貼士 1-007-004-000 ==========
    ErrorCode TIP_NOT_EXISTS = new ErrorCode(1_007_004_001, "烹飪小貼士不存在");
    ErrorCode TIP_DISH_NOT_MATCH = new ErrorCode(1_007_004_002, "烹飪小貼士與菜品不匹配");

    // ========== 評分 1-007-005-000 ==========
    ErrorCode STAR_RATING_NOT_EXISTS = new ErrorCode(1_007_005_001, "評分不存在");
    ErrorCode STAR_RATING_DUPLICATE = new ErrorCode(1_007_005_002, "已對此菜品評分");
    
} 