package cn.iocoder.yudao.module.cooking.controller.app.ingredient;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.cooking.controller.app.ingredient.vo.*;
import cn.iocoder.yudao.module.cooking.convert.ingredient.IngredientAppConvert;
import cn.iocoder.yudao.module.cooking.dal.dataobject.DishDO;
import cn.iocoder.yudao.module.cooking.dal.dataobject.IngredientDO;
import cn.iocoder.yudao.module.cooking.service.dish.DishService;
import cn.iocoder.yudao.module.cooking.service.ingredient.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 用戶 APP - 配料
 */
@Tag(name = "用戶 APP - 配料")
@RestController("appIngredientController")
@RequestMapping("/cooking/ingredient")
@Validated
public class IngredientController {

    @Resource
    private IngredientService ingredientService;
    
    @Resource
    private DishService dishService;

    @GetMapping("/get")
    @Operation(summary = "獲得配料")
    @Parameter(name = "id", description = "編號", required = true, example = "1024")
    public CommonResult<IngredientRespVO> getIngredient(@RequestParam("id") Long id) {
        IngredientDO ingredient = ingredientService.getIngredient(id);
        return success(buildIngredientRespVO(ingredient));
    }

    @GetMapping("/list")
    @Operation(summary = "獲得指定菜品的配料列表")
    public CommonResult<List<IngredientRespVO>> getIngredientListByDishId(@Valid IngredientListReqVO reqVO) {
        // 查詢菜品信息
        DishDO dish = dishService.getDish(reqVO.getDishId());
        
        // 校驗菜品是否存在
        if (dish == null) {
            return success(List.of());
        }
        
        // 查詢配料列表
        List<IngredientDO> list = ingredientService.getIngredientListByDishId(reqVO.getDishId());
        
        // 構建 VO 對象，並設置菜品名稱
        return success(list.stream()
                .map(ingredient -> {
                    IngredientRespVO respVO = IngredientAppConvert.INSTANCE.convert(ingredient);
                    respVO.setDishName(dish.getName());
                    return respVO;
                })
                .collect(Collectors.toList()));
    }
    
    /**
     * 構建配料響應 VO，添加菜品名稱
     *
     * @param ingredient 配料DO
     * @return 配料響應 VO
     */
    // 在 buildIngredientRespVO 方法中使用 IngredientAppConvert
    private IngredientRespVO buildIngredientRespVO(IngredientDO ingredient) {
        return IngredientAppConvert.INSTANCE.convert(ingredient);
    }
}