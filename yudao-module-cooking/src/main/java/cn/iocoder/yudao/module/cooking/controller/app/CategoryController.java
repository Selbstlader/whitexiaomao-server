package cn.iocoder.yudao.module.cooking.controller.app;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.cooking.controller.app.vo.category.CategoryRespVO;
import cn.iocoder.yudao.module.cooking.convert.category.CategoryConvert;
import cn.iocoder.yudao.module.cooking.dal.dataobject.category.CategoryDO;
import cn.iocoder.yudao.module.cooking.service.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.List;

@Tag(name = "用户 APP - 菜谱分类")
@RestController
@RequestMapping("/cooking/category")
@Validated
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/list")
    @Operation(summary = "获取分类列表")
    public CommonResult<List<CategoryRespVO>> getCategoryList() {
        List<CategoryDO> list = categoryService.getCategoryList();
        return CommonResult.success(CategoryConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/recipe-categories")
    @Operation(summary = "获取菜谱分类列表")
    public CommonResult<List<CategoryRespVO>> getRecipeCategories() {
        List<CategoryDO> list = categoryService.getRecipeCategories();
        return CommonResult.success(CategoryConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/ingredient-categories")
    @Operation(summary = "获取食材分类列表")
    public CommonResult<List<CategoryRespVO>> getIngredientCategories() {
        List<CategoryDO> list = categoryService.getIngredientCategories();
        return CommonResult.success(CategoryConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取分类详情")
    @Parameter(name = "id", description = "分类编号", required = true)
    public CommonResult<CategoryRespVO> getCategory(@PathVariable("id") Long id) {
        CategoryDO category = categoryService.getCategory(id);
        return CommonResult.success(CategoryConvert.INSTANCE.convert(category));
    }
} 