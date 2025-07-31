package cn.iocoder.yudao.module.cooking.controller.admin.ingredient;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.cooking.controller.admin.ingredient.vo.*;
import cn.iocoder.yudao.module.cooking.convert.ingredient.IngredientConvert;
import cn.iocoder.yudao.module.cooking.dal.dataobject.DishDO;
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
import java.util.stream.Collectors;

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
        return success(buildIngredientRespVO(ingredient));
    }

    @GetMapping("/list")
    @Operation(summary = "獲得指定菜品的配料列表")
    @PreAuthorize("@ss.hasPermission('cooking:ingredient:query')")
    public CommonResult<List<IngredientRespVO>> getIngredientListByDishId(@Valid IngredientListReqVO reqVO) {
        // 查詢菜品信息
        DishDO dish = dishService.getDish(reqVO.getDishId());
        
        // 校驗菜品是否存在
        if (dish == null) {
            return success(List.of());
        }
        
        // 查詢配料列表
        List<IngredientDO> list = ingredientService.getIngredientListByDishId(reqVO.getDishId());
        
        // 打印調試信息
        System.out.println("=== DEBUG: 查詢配料，dishId=" + reqVO.getDishId() + ", 獲取到配料數量: " + list.size() + " ===");
        
        // 構建 VO 對象，並設置菜品名稱
        return success(list.stream()
                .map(ingredient -> {
                    IngredientRespVO respVO = IngredientConvert.INSTANCE.convert(ingredient);
                    respVO.setDishName(dish.getName());
                    return respVO;
                })
                .collect(Collectors.toList()));
    }

    @PostMapping("/batch-create")
    @Operation(summary = "批量創建配料")
    @PreAuthorize("@ss.hasPermission('cooking:ingredient:create')")
    public CommonResult<List<Long>> batchCreateIngredient(@Valid @RequestBody IngredientBatchCreateReqVO batchCreateReqVO) {
        return success(ingredientService.batchCreateIngredient(batchCreateReqVO));
    }
    
    /**
     * 構建配料響應 VO，添加菜品名稱
     *
     * @param ingredient 配料DO
     * @return 配料響應 VO
     */
    private IngredientRespVO buildIngredientRespVO(IngredientDO ingredient) {
        if (ingredient == null) {
            return null;
        }
        
        IngredientRespVO respVO = IngredientConvert.INSTANCE.convert(ingredient);
        
        // 設置菜品名稱
        DishDO dish = dishService.getDish(ingredient.getDishId());
        if (dish != null) {
            respVO.setDishName(dish.getName());
        }
        
        return respVO;
    }
}