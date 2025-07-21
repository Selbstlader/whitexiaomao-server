package cn.iocoder.yudao.module.cooking.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * Cooking 错误码枚举类
 * 
 * cooking 系统，使用 1-004-000-000 段
 *
 * @author 芋道源码
 */
public interface ErrorCodeConstants {

    // ========== Cooking 通用错误 1-004-001-000 ==========
    ErrorCode COOKING_API_ERROR = new ErrorCode(1_004_001_000, "烹饪 API 调用失败");
    ErrorCode COOKING_CONFIG_ERROR = new ErrorCode(1_004_001_001, "烹饪配置错误");

    // ========== 菜谱相关错误 1-004-002-000 ==========
    ErrorCode RECIPE_NOT_EXISTS = new ErrorCode(1_004_002_000, "菜谱不存在");
    ErrorCode RECIPE_NAME_DUPLICATE = new ErrorCode(1_004_002_001, "菜谱名称已存在");
    ErrorCode RECIPE_CATEGORY_NOT_EXISTS = new ErrorCode(1_004_002_002, "菜谱分类不存在");
    ErrorCode RECIPE_DIFFICULTY_INVALID = new ErrorCode(1_004_002_003, "菜谱难度无效");
    ErrorCode RECIPE_COOKING_TIME_INVALID = new ErrorCode(1_004_002_004, "烹饪时间无效");

    // ========== 食材相关错误 1-004-003-000 ==========
    ErrorCode INGREDIENT_NOT_EXISTS = new ErrorCode(1_004_003_000, "食材不存在");
    ErrorCode INGREDIENT_NAME_DUPLICATE = new ErrorCode(1_004_003_001, "食材名称已存在");
    ErrorCode INGREDIENT_CATEGORY_NOT_EXISTS = new ErrorCode(1_004_003_002, "食材分类不存在");
    ErrorCode INGREDIENT_NUTRITION_INVALID = new ErrorCode(1_004_003_003, "食材营养信息无效");

    // ========== 烹饪步骤相关错误 1-004-004-000 ==========
    ErrorCode COOKING_STEP_NOT_EXISTS = new ErrorCode(1_004_004_000, "烹饪步骤不存在");
    ErrorCode COOKING_STEP_ORDER_INVALID = new ErrorCode(1_004_004_001, "烹饪步骤顺序无效");
    ErrorCode COOKING_STEP_TIME_INVALID = new ErrorCode(1_004_004_002, "烹饪步骤时间无效");
    ErrorCode COOKING_STEP_TEMPERATURE_INVALID = new ErrorCode(1_004_004_003, "烹饪温度无效");

    // ========== 分类相关错误 1-004-005-000 ==========
    ErrorCode CATEGORY_NOT_EXISTS = new ErrorCode(1_004_005_000, "分类不存在");
    ErrorCode CATEGORY_NAME_DUPLICATE = new ErrorCode(1_004_005_001, "分类名称已存在");
    ErrorCode CATEGORY_HAS_CHILDREN = new ErrorCode(1_004_005_002, "分类下存在子分类，无法删除");
    ErrorCode CATEGORY_HAS_RECIPES = new ErrorCode(1_004_005_003, "分类下存在菜谱，无法删除");

    // ========== 营养分析相关错误 1-004-006-000 ==========
    ErrorCode NUTRITION_ANALYSIS_FAILED = new ErrorCode(1_004_006_000, "营养分析失败");
    ErrorCode NUTRITION_DATA_INVALID = new ErrorCode(1_004_006_001, "营养数据无效");
    ErrorCode NUTRITION_API_UNAVAILABLE = new ErrorCode(1_004_006_002, "营养分析API不可用");

    // ========== 用户相关错误 1-004-007-000 ==========
    ErrorCode USER_RECIPE_NOT_EXISTS = new ErrorCode(1_004_007_000, "用户菜谱不存在");
    ErrorCode USER_RECIPE_ALREADY_EXISTS = new ErrorCode(1_004_007_001, "用户菜谱已存在");
    ErrorCode COOKING_RECORD_NOT_EXISTS = new ErrorCode(1_004_007_002, "烹饪记录不存在");
    ErrorCode SHOPPING_LIST_NOT_EXISTS = new ErrorCode(1_004_007_003, "购物清单不存在");

    // ========== 文件上传相关错误 1-004-008-000 ==========
    ErrorCode FILE_SIZE_EXCEEDED = new ErrorCode(1_004_008_000, "文件大小超出限制");
    ErrorCode FILE_TYPE_NOT_ALLOWED = new ErrorCode(1_004_008_001, "文件类型不允许");
    ErrorCode IMAGE_UPLOAD_FAILED = new ErrorCode(1_004_008_002, "图片上传失败");

}
