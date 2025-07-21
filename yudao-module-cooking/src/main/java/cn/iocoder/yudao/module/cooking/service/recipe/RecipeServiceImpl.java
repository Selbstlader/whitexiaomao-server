package cn.iocoder.yudao.module.cooking.service.recipe;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.cooking.controller.admin.recipe.vo.*;
import cn.iocoder.yudao.module.cooking.controller.app.vo.recipe.*;
import cn.iocoder.yudao.module.cooking.convert.recipe.RecipeConvert;
import cn.iocoder.yudao.module.cooking.dal.dataobject.recipe.RecipeDO;
import cn.iocoder.yudao.module.cooking.dal.mysql.recipe.RecipeMapper;
import cn.iocoder.yudao.module.cooking.enums.RecipeStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.cooking.enums.ErrorCodeConstants.*;

/**
 * 菜谱 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
public class RecipeServiceImpl implements RecipeService {

    @Resource
    private RecipeMapper recipeMapper;

    @Override
    public Long createRecipe(RecipeCreateReqVO createReqVO) {
        // 插入
        RecipeDO recipe = RecipeConvert.INSTANCE.convert(createReqVO);
        recipe.setStatus(RecipeStatusEnum.DRAFT.getStatus());
        recipe.setViewCount(0);
        recipe.setFavoriteCount(0);
        recipe.setRatingCount(0);
        recipeMapper.insert(recipe);
        // 返回
        return recipe.getId();
    }

    @Override
    public void updateRecipe(RecipeUpdateReqVO updateReqVO) {
        // 校验存在
        validateRecipeExists(updateReqVO.getId());
        // 更新
        RecipeDO updateObj = RecipeConvert.INSTANCE.convert(updateReqVO);
        recipeMapper.updateById(updateObj);
    }

    @Override
    public void deleteRecipe(Long id) {
        // 校验存在
        validateRecipeExists(id);
        // 删除
        recipeMapper.deleteById(id);
    }

    private RecipeDO validateRecipeExists(Long id) {
        RecipeDO recipe = recipeMapper.selectById(id);
        if (recipe == null) {
            throw exception(RECIPE_NOT_EXISTS);
        }
        return recipe;
    }

    @Override
    public RecipeDO getRecipe(Long id) {
        return recipeMapper.selectById(id);
    }

    @Override
    public PageResult<RecipeDO> getRecipePage(cn.iocoder.yudao.module.cooking.controller.admin.recipe.vo.RecipePageReqVO pageReqVO) {
        return recipeMapper.selectPage(pageReqVO);
    }

    @Override
    public List<RecipeDO> getRecipeList(RecipeExportReqVO exportReqVO) {
        return recipeMapper.selectList(exportReqVO);
    }

    @Override
    @Transactional
    public void publishRecipe(Long id) {
        // 校验存在
        RecipeDO recipe = validateRecipeExists(id);

        // 更新状态
        RecipeDO updateObj = new RecipeDO();
        updateObj.setId(id);
        updateObj.setStatus(RecipeStatusEnum.PUBLISHED.getStatus());
        recipeMapper.updateById(updateObj);
    }

    @Override
    @Transactional
    public void disableRecipe(Long id) {
        // 校验存在
        RecipeDO recipe = validateRecipeExists(id);

        // 更新状态
        RecipeDO updateObj = new RecipeDO();
        updateObj.setId(id);
        updateObj.setStatus(RecipeStatusEnum.DISABLED.getStatus());
        recipeMapper.updateById(updateObj);
    }

    @Override
    @Transactional
    public void incrementViewCount(Long id) {
        // 校验存在
        validateRecipeExists(id);

        // 增加浏览次数
        RecipeDO recipe = recipeMapper.selectById(id);
        RecipeDO updateObj = new RecipeDO();
        updateObj.setId(id);
        updateObj.setViewCount(recipe.getViewCount() + 1);
        recipeMapper.updateById(updateObj);
    }

    @Override
    @Transactional
    public void favoriteRecipe(Long id, Long userId) {
        // 校验存在
        validateRecipeExists(id);
        
        // TODO: 实现收藏逻辑，需要创建用户收藏表
        log.info("用户 {} 收藏菜谱 {}", userId, id);
    }

    @Override
    @Transactional
    public void unfavoriteRecipe(Long id, Long userId) {
        // 校验存在
        validateRecipeExists(id);
        
        // TODO: 实现取消收藏逻辑
        log.info("用户 {} 取消收藏菜谱 {}", userId, id);
    }

    @Override
    @Transactional
    public void rateRecipe(Long id, Long userId, Integer rating) {
        // 校验存在
        validateRecipeExists(id);
        
        // 校验评分范围
        if (rating < 1 || rating > 5) {
            throw exception(RECIPE_DIFFICULTY_INVALID);
        }
        
        // TODO: 实现评分逻辑，需要创建评分表
        log.info("用户 {} 对菜谱 {} 评分 {}", userId, id, rating);
    }

    // =================== APP端接口实现 ===================

    @Override
    public PageResult<RecipeVO> getRecipePage(cn.iocoder.yudao.module.cooking.controller.app.vo.recipe.RecipePageReqVO pageReqVO) {
        PageResult<RecipeDO> pageResult = getRecipePage(RecipeConvert.INSTANCE.convert(pageReqVO));
        return RecipeConvert.INSTANCE.convertPage(pageResult);
    }

    @Override
    public RecipeDetailVO getRecipeDetail(Long id) {
        RecipeDO recipe = getRecipe(id);
        return RecipeConvert.INSTANCE.convertDetail(recipe);
    }

    @Override
    public List<RecipeVO> getPopularRecipes() {
        // TODO 待实现获取热门菜谱
        return Collections.emptyList();
    }

    @Override
    public List<RecipeVO> getRecipesByCategory(Long categoryId) {
        // TODO 待实现按分类获取菜谱
        return Collections.emptyList();
    }

    @Override
    public void favoriteRecipe(Long id) {
        // 假设从上下文获取当前用户ID
        Long userId = 1L; // TODO 替换为实际的用户ID获取方式
        favoriteRecipe(id, userId);
    }

    @Override
    public void unfavoriteRecipe(Long id) {
        // 假设从上下文获取当前用户ID
        Long userId = 1L; // TODO 替换为实际的用户ID获取方式
        unfavoriteRecipe(id, userId);
    }

    @Override
    public List<RecipeVO> getFavoriteRecipes() {
        // 假设从上下文获取当前用户ID
        Long userId = 1L; // TODO 替换为实际的用户ID获取方式
        // TODO 待实现获取用户收藏的菜谱
        return Collections.emptyList();
    }

}
