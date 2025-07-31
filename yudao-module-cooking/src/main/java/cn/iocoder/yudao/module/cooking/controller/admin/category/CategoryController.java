package cn.iocoder.yudao.module.cooking.controller.admin.category;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.cooking.controller.admin.category.vo.*;
import cn.iocoder.yudao.module.cooking.convert.category.CategoryConvert;
import cn.iocoder.yudao.module.cooking.dal.dataobject.category.CategoryDO;
import cn.iocoder.yudao.module.cooking.service.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 管理後台 - 菜品分類
 */
@Tag(name = "管理後台 - 菜品分類")
@RestController
@RequestMapping("/cooking/category")  // 修改为 admin-api
@Validated
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @PostMapping("/create")
    @Operation(summary = "創建菜品分類")
    // @PreAuthorize("@ss.hasPermission('cooking:category:create')") // 注释掉权限控制
    public CommonResult<Long> createCategory(@Valid @RequestBody CategoryCreateReqVO createReqVO) {
        return success(categoryService.createCategory(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新菜品分類")
    // @PreAuthorize("@ss.hasPermission('cooking:category:update')") // 注释掉权限控制
    public CommonResult<Boolean> updateCategory(@Valid @RequestBody CategoryUpdateReqVO updateReqVO) {
        categoryService.updateCategory(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "刪除菜品分類")
    @Parameter(name = "id", description = "編號", required = true)
    // @PreAuthorize("@ss.hasPermission('cooking:category:delete')") // 注释掉权限控制
    public CommonResult<Boolean> deleteCategory(@RequestParam("id") Long id) {
        categoryService.deleteCategory(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "獲得菜品分類")
    @Parameter(name = "id", description = "編號", required = true, example = "1024")
    // @PreAuthorize("@ss.hasPermission('cooking:category:query')") // 注释掉权限控制
    public CommonResult<CategoryRespVO> getCategory(@RequestParam("id") Long id) {
        CategoryDO category = categoryService.getCategory(id);
        return success(CategoryConvert.INSTANCE.convert(category));
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "獲得所有菜品分類精簡列表", description = "主要用於前端的下拉選項")
    public CommonResult<List<CategorySimpleRespVO>> getSimpleCategoryList() {
        List<CategoryDO> list = categoryService.getCategoryList();
        return success(CategoryConvert.INSTANCE.convertList02(list));
    }

    @GetMapping("/page")
    @Operation(summary = "獲得菜品分類分頁")
    // @PreAuthorize("@ss.hasPermission('cooking:category:query')") // 注释掉权限控制
    public CommonResult<PageResult<CategoryRespVO>> getCategoryPage(@Valid CategoryPageReqVO pageVO) {
        PageResult<CategoryDO> pageResult = categoryService.getCategoryPage(pageVO);
        return success(CategoryConvert.INSTANCE.convertPage(pageResult));
    }

}