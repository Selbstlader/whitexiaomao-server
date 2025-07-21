package cn.iocoder.yudao.module.cooking.controller.app;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.cooking.controller.app.vo.ingredient.IngredientPageReqVO;
import cn.iocoder.yudao.module.cooking.controller.app.vo.ingredient.IngredientRespVO;
import cn.iocoder.yudao.module.cooking.convert.ingredient.IngredientConvert;
import cn.iocoder.yudao.module.cooking.dal.dataobject.ingredient.IngredientDO;
import cn.iocoder.yudao.module.cooking.service.ingredient.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;

@Tag(name = "用户 APP - 食材")
@RestController
@RequestMapping("/cooking/ingredient")
@Validated
public class IngredientController {

    @Resource
    private IngredientService ingredientService;

    @GetMapping("/page")
    @Operation(summary = "获取食材分页列表")
    public CommonResult<PageResult<IngredientRespVO>> getIngredientPage(@Valid IngredientPageReqVO pageVO) {
        PageResult<IngredientDO> pageResult = ingredientService.getIngredientPage(
                IngredientConvert.INSTANCE.convert(pageVO));
        return CommonResult.success(IngredientConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/list")
    @Operation(summary = "获取所有食材列表")
    public CommonResult<List<IngredientRespVO>> getIngredientList() {
        List<IngredientDO> list = ingredientService.getIngredientList(null);
        return CommonResult.success(IngredientConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取食材详情")
    @Parameter(name = "id", description = "食材编号", required = true)
    public CommonResult<IngredientRespVO> getIngredient(@PathVariable("id") Long id) {
        IngredientDO ingredient = ingredientService.getIngredient(id);
        return CommonResult.success(IngredientConvert.INSTANCE.convert(ingredient));
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "获取指定分类下的食材列表")
    @Parameter(name = "categoryId", description = "分类编号", required = true)
    public CommonResult<List<IngredientRespVO>> getIngredientsByCategory(@PathVariable("categoryId") Long categoryId) {
        List<IngredientDO> list = ingredientService.getIngredientListByCategory(categoryId);
        return CommonResult.success(IngredientConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/search")
    @Operation(summary = "搜索食材")
    @Parameter(name = "keyword", description = "搜索关键词", required = true)
    public CommonResult<List<IngredientRespVO>> searchIngredients(String keyword) {
        List<IngredientDO> list = ingredientService.searchIngredients(keyword);
        return CommonResult.success(IngredientConvert.INSTANCE.convertList(list));
    }
} 