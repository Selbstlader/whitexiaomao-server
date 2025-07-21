package cn.iocoder.yudao.module.cooking.service.sync;

import cn.iocoder.yudao.module.cooking.dal.dataobject.recipe.RecipeDO;

import java.util.List;

/**
 * 烹饪数据同步 Service 接口
 * 用于从外部数据源（如 cook.aiursoft.cn）同步菜谱数据
 *
 * @author 芋道源码
 */
public interface CookingSyncService {

    /**
     * 同步菜谱分类数据
     * 从 cook.aiursoft.cn 获取所有菜谱分类
     */
    void syncCategories();

    /**
     * 同步菜谱数据
     * 从 cook.aiursoft.cn 获取所有菜谱
     */
    void syncRecipes();

    /**
     * 同步单个菜谱数据
     * 
     * @param recipeUrl 菜谱URL
     * @return 菜谱数据
     */
    RecipeDO syncSingleRecipe(String recipeUrl);

    /**
     * 获取所有菜谱链接
     * 
     * @return 菜谱链接列表
     */
    List<String> getAllRecipeUrls();

    /**
     * 解析菜谱页面内容
     * 
     * @param recipeUrl 菜谱URL
     * @param htmlContent 页面HTML内容
     * @return 菜谱数据
     */
    RecipeDO parseRecipeContent(String recipeUrl, String htmlContent);

    /**
     * 检查菜谱是否已存在
     * 
     * @param sourceUrl 来源URL
     * @return 是否存在
     */
    boolean isRecipeExists(String sourceUrl);

}
