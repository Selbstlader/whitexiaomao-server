package cn.iocoder.yudao.module.cooking.controller.admin.ingredient;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.cooking.controller.admin.ingredient.vo.*;
import cn.iocoder.yudao.module.cooking.convert.ingredient.IngredientConvert;
import cn.iocoder.yudao.module.cooking.dal.dataobject.IngredientDO;
import cn.iocoder.yudao.module.cooking.service.dish.DishService;
import cn.iocoder.yudao.module.cooking.service.ingredient.IngredientService;
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
 * 管理後台 - 配料
 */
@Tag(name = "管理後台 - 配料")
@RestController
@RequestMapping("/cooking/ingredient")
@Validated
public class IngredientController {

    @Resource
    private IngredientService ingredientService;
    
    @Resource
    private DishService dishService;

    @PostMapping("/create")
    @Operation(summary = "創建配料")
    @PreAuthorize("@ss.hasPermission('cooking:ingredient:create')")
    public CommonResult<Long> createIngredient(@Valid @RequestBody IngredientCreateReqVO createReqVO) {
        return success(ingredientService.createIngredient(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新配料")
    @PreAuthorize("@ss.hasPermission('cooking:ingredient:update')")
    public CommonResult<Boolean> updateIngredient(@Valid @RequestBody IngredientUpdateReqVO updateReqVO) {
        ingredientService.updateIngredient(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "刪除配料")
    @Parameter(name = "id", description = "編號", required = true)
    @PreAuthorize("@ss.hasPermission('cooking:ingredient:delete')")
    public CommonResult<Boolean> deleteIngredient(@RequestParam("id") Long id) {
        ingredientService.deleteIngredient(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "獲得配料")
    @Parameter(name = "id", description = "編號", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cooking:ingredient:query')")
    public CommonResult<IngredientRespVO> getIngredient(@RequestParam("id") Long id) {
        IngredientDO ingredient = ingredientService.getIngredient(id);
        return success(IngredientConvert.INSTANCE.convert(ingredient));
    }

    @GetMapping("/list")
    @Operation(summary = "獲得指定菜品的配料列表")
    @PreAuthorize("@ss.hasPermission('cooking:ingredient:query')")
    public CommonResult<List<IngredientRespVO>> getIngredientListByDishId(@Valid IngredientListReqVO reqVO) {
        // 校驗菜品是否存在
        if (dishService.getDish(reqVO.getDishId()) == null) {
            return success(List.of());
        }
        List<IngredientDO> list = ingredientService.getIngredientListByDishId(reqVO.getDishId());
        return success(IngredientConvert.INSTANCE.convertList(list));
    }

    @PostMapping("/batch-create")
    @Operation(summary = "批量創建配料")
    @PreAuthorize("@ss.hasPermission('cooking:ingredient:create')")
    public CommonResult<List<Long>> batchCreateIngredient(@Valid @RequestBody IngredientBatchCreateReqVO batchCreateReqVO) {
        return success(ingredientService.batchCreateIngredient(batchCreateReqVO));
    }

} 