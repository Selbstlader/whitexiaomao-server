package cn.iocoder.yudao.module.cooking.controller.app;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.cooking.controller.app.vo.recipe.*;
import cn.iocoder.yudao.module.cooking.service.recipe.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;

@Tag(name = "用户 APP - 菜谱信息")
@RestController
@RequestMapping("/cooking/app/recipe")
@Validated
public class AppRecipeController {

    @Resource
    private RecipeService recipeService;

    @GetMapping("/page")
    @Operation(summary = "获取菜谱分页列表")
    public CommonResult<PageResult<RecipeVO>> getRecipePage(@Valid RecipePageReqVO pageVO) {
        return CommonResult.success(recipeService.getRecipePage(pageVO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取菜谱详情")
    @Parameter(name = "id", description = "菜谱编号", required = true)
    public CommonResult<RecipeDetailVO> getRecipeDetail(@PathVariable("id") Long id) {
        return CommonResult.success(recipeService.getRecipeDetail(id));
    }

    @GetMapping("/popular")
    @Operation(summary = "获取热门菜谱")
    public CommonResult<List<RecipeVO>> getPopularRecipes() {
        return CommonResult.success(recipeService.getPopularRecipes());
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "获取分类下的菜谱")
    @Parameter(name = "categoryId", description = "分类编号", required = true)
    public CommonResult<List<RecipeVO>> getRecipesByCategory(@PathVariable("categoryId") Long categoryId) {
        return CommonResult.success(recipeService.getRecipesByCategory(categoryId));
    }

    @PostMapping("/{id}/favorite")
    @Operation(summary = "收藏菜谱")
    @Parameter(name = "id", description = "菜谱编号", required = true)
    public CommonResult<Boolean> favoriteRecipe(@PathVariable("id") Long id) {
        recipeService.favoriteRecipe(id);
        return CommonResult.success(true);
    }

    @DeleteMapping("/{id}/favorite")
    @Operation(summary = "取消收藏菜谱")
    @Parameter(name = "id", description = "菜谱编号", required = true)
    public CommonResult<Boolean> unfavoriteRecipe(@PathVariable("id") Long id) {
        recipeService.unfavoriteRecipe(id);
        return CommonResult.success(true);
    }

    @GetMapping("/favorites")
    @Operation(summary = "获取收藏的菜谱列表")
    public CommonResult<List<RecipeVO>> getFavoriteRecipes() {
        return CommonResult.success(recipeService.getFavoriteRecipes());
    }
} 