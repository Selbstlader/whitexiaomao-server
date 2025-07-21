package cn.iocoder.yudao.module.cooking.controller.admin.sync;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum;
import cn.iocoder.yudao.module.cooking.service.sync.CookingSyncService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

@Tag(name = "管理后台 - 烹饪数据同步")
@RestController
@RequestMapping("/cooking/sync")
@Validated
public class CookingSyncController {

    @Resource
    private CookingSyncService cookingSyncService;

    @PostMapping("/categories")
    @Operation(summary = "同步菜谱分类")
    @PreAuthorize("@ss.hasPermission('cooking:sync:categories')")
    @ApiAccessLog(operateType = OperateTypeEnum.IMPORT)
    public CommonResult<Boolean> syncCategories() {
        cookingSyncService.syncCategories();
        return success(true);
    }

    @PostMapping("/recipes")
    @Operation(summary = "同步菜谱数据")
    @PreAuthorize("@ss.hasPermission('cooking:sync:recipes')")
    @ApiAccessLog(operateType = OperateTypeEnum.IMPORT)
    public CommonResult<Boolean> syncRecipes() {
        cookingSyncService.syncRecipes();
        return success(true);
    }

    @PostMapping("/single-recipe")
    @Operation(summary = "同步单个菜谱")
    @PreAuthorize("@ss.hasPermission('cooking:sync:single-recipe')")
    @ApiAccessLog(operateType = OperateTypeEnum.IMPORT)
    public CommonResult<Boolean> syncSingleRecipe(@RequestParam("url") String recipeUrl) {
        cookingSyncService.syncSingleRecipe(recipeUrl);
        return success(true);
    }

    @GetMapping("/recipe-urls")
    @Operation(summary = "获取所有菜谱链接")
    @PreAuthorize("@ss.hasPermission('cooking:sync:query')")
    public CommonResult<java.util.List<String>> getAllRecipeUrls() {
        return success(cookingSyncService.getAllRecipeUrls());
    }

}
