package cn.iocoder.yudao.module.cooking.service.recipe;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.cooking.controller.admin.recipe.vo.*;
import cn.iocoder.yudao.module.cooking.controller.app.vo.recipe.*;
import cn.iocoder.yudao.module.cooking.dal.dataobject.recipe.RecipeDO;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 菜谱 Service 接口
 *
 * @author 芋道源码
 */
public interface RecipeService {

    /**
     * 创建菜谱
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createRecipe(@Valid RecipeCreateReqVO createReqVO);

    /**
     * 更新菜谱
     *
     * @param updateReqVO 更新信息
     */
    void updateRecipe(@Valid RecipeUpdateReqVO updateReqVO);

    /**
     * 删除菜谱
     *
     * @param id 编号
     */
    void deleteRecipe(Long id);

    /**
     * 获得菜谱
     *
     * @param id 编号
     * @return 菜谱
     */
    RecipeDO getRecipe(Long id);

    /**
     * 获得菜谱分页
     *
     * @param pageReqVO 分页查询
     * @return 菜谱分页
     */
    PageResult<RecipeDO> getRecipePage(cn.iocoder.yudao.module.cooking.controller.admin.recipe.vo.RecipePageReqVO pageReqVO);

    /**
     * 获得菜谱列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 菜谱列表
     */
    List<RecipeDO> getRecipeList(RecipeExportReqVO exportReqVO);

    /**
     * 发布菜谱
     *
     * @param id 菜谱编号
     */
    void publishRecipe(Long id);

    /**
     * 禁用菜谱
     *
     * @param id 菜谱编号
     */
    void disableRecipe(Long id);

    /**
     * 增加浏览次数
     *
     * @param id 菜谱编号
     */
    void incrementViewCount(Long id);

    /**
     * 收藏菜谱
     *
     * @param id 菜谱编号
     * @param userId 用户编号
     */
    void favoriteRecipe(Long id, Long userId);

    /**
     * 取消收藏菜谱
     *
     * @param id 菜谱编号
     * @param userId 用户编号
     */
    void unfavoriteRecipe(Long id, Long userId);

    /**
     * 评分菜谱
     *
     * @param id 菜谱编号
     * @param userId 用户编号
     * @param rating 评分（1-5星）
     */
    void rateRecipe(Long id, Long userId, Integer rating);

    // =================== APP端接口 ===================

    /**
     * 获得菜谱分页 (APP端)
     *
     * @param pageReqVO 分页查询
     * @return 菜谱分页
     */
    PageResult<RecipeVO> getRecipePage(cn.iocoder.yudao.module.cooking.controller.app.vo.recipe.RecipePageReqVO pageReqVO);

    /**
     * 获得菜谱详情 (APP端)
     *
     * @param id 菜谱编号
     * @return 菜谱详情
     */
    RecipeDetailVO getRecipeDetail(Long id);

    /**
     * 获得热门菜谱列表
     *
     * @return 热门菜谱列表
     */
    List<RecipeVO> getPopularRecipes();

    /**
     * 根据分类获得菜谱列表
     *
     * @param categoryId 分类编号
     * @return 菜谱列表
     */
    List<RecipeVO> getRecipesByCategory(Long categoryId);

    /**
     * 收藏菜谱 (APP端)
     *
     * @param id 菜谱编号
     */
    void favoriteRecipe(Long id);

    /**
     * 取消收藏菜谱 (APP端)
     *
     * @param id 菜谱编号
     */
    void unfavoriteRecipe(Long id);

    /**
     * 获得收藏的菜谱列表
     *
     * @return 收藏的菜谱列表
     */
    List<RecipeVO> getFavoriteRecipes();

}
