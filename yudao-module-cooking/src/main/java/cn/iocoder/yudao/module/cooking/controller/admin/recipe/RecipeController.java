package cn.iocoder.yudao.module.cooking.controller.admin.recipe;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum;
import cn.iocoder.yudao.module.cooking.controller.admin.recipe.vo.*;
import cn.iocoder.yudao.module.cooking.convert.recipe.RecipeConvert;
import cn.iocoder.yudao.module.cooking.dal.dataobject.recipe.RecipeDO;
import cn.iocoder.yudao.module.cooking.service.recipe.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

@Tag(name = "管理后台 - 菜谱")
@RestController
@RequestMapping("/cooking/recipe")
@Validated
public class RecipeController {

    @Resource
    private RecipeService recipeService;

    @PostMapping("/create")
    @Operation(summary = "创建菜谱")
    @PreAuthorize("@ss.hasPermission('cooking:recipe:create')")
    public CommonResult<Long> createRecipe(@Valid @RequestBody RecipeCreateReqVO createReqVO) {
        return success(recipeService.createRecipe(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新菜谱")
    @PreAuthorize("@ss.hasPermission('cooking:recipe:update')")
    public CommonResult<Boolean> updateRecipe(@Valid @RequestBody RecipeUpdateReqVO updateReqVO) {
        recipeService.updateRecipe(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除菜谱")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cooking:recipe:delete')")
    public CommonResult<Boolean> deleteRecipe(@RequestParam("id") Long id) {
        recipeService.deleteRecipe(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得菜谱")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cooking:recipe:query')")
    public CommonResult<RecipeRespVO> getRecipe(@RequestParam("id") Long id) {
        RecipeDO recipe = recipeService.getRecipe(id);
        return success(RecipeConvert.INSTANCE.convertToRespVO(recipe));
    }

    @GetMapping("/page")
    @Operation(summary = "获得菜谱分页")
    @PreAuthorize("@ss.hasPermission('cooking:recipe:query')")
    public CommonResult<PageResult<RecipeRespVO>> getRecipePage(@Valid RecipePageReqVO pageReqVO) {
        PageResult<RecipeDO> pageResult = recipeService.getRecipePage(pageReqVO);
        return success(RecipeConvert.INSTANCE.convertToRespVOPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出菜谱 Excel")
    @PreAuthorize("@ss.hasPermission('cooking:recipe:export')")
    @ApiAccessLog(operateType = OperateTypeEnum.EXPORT)
    public void exportRecipeExcel(@Valid RecipeExportReqVO exportReqVO,
                                  HttpServletResponse response) throws IOException {
        List<RecipeDO> list = recipeService.getRecipeList(exportReqVO);
        // 导出 Excel
        ExcelUtils.write(response, "菜谱.xls", "数据", RecipeExcelVO.class,
                RecipeConvert.INSTANCE.convertList02(list));
    }

    @PutMapping("/publish")
    @Operation(summary = "发布菜谱")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cooking:recipe:update')")
    public CommonResult<Boolean> publishRecipe(@RequestParam("id") Long id) {
        recipeService.publishRecipe(id);
        return success(true);
    }

    @PutMapping("/disable")
    @Operation(summary = "禁用菜谱")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cooking:recipe:update')")
    public CommonResult<Boolean> disableRecipe(@RequestParam("id") Long id) {
        recipeService.disableRecipe(id);
        return success(true);
    }

}
