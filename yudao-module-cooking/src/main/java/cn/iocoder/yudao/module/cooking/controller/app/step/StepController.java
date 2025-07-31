package cn.iocoder.yudao.module.cooking.controller.app.step;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.cooking.controller.app.step.vo.*;
import cn.iocoder.yudao.module.cooking.convert.step.StepAppConvert;
import cn.iocoder.yudao.module.cooking.dal.dataobject.DishDO;
import cn.iocoder.yudao.module.cooking.dal.dataobject.StepDO;
import cn.iocoder.yudao.module.cooking.service.dish.DishService;
import cn.iocoder.yudao.module.cooking.service.step.StepService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 用戶 APP - 烹飪步驟
 */
@Tag(name = "用戶 APP - 烹飪步驟")
@RestController("appStepController")
@RequestMapping("/cooking/step")
@Validated
public class StepController {

    @Resource
    private StepService stepService;

    @Resource
    private DishService dishService;

    @GetMapping("/get")
    @Operation(summary = "獲得烹飪步驟")
    @Parameter(name = "id", description = "編號", required = true, example = "1024")
    public CommonResult<StepRespVO> getStep(@RequestParam("id") Long id) {
        StepDO step = stepService.getStep(id);
        return success(buildStepRespVO(step));
    }

    @GetMapping("/list")
    @Operation(summary = "獲得指定菜品的烹飪步驟列表")
    public CommonResult<List<StepRespVO>> getStepListByDishId(@Valid StepListReqVO reqVO) {
        // 校驗菜品是否存在
        DishDO dish = dishService.getDish(reqVO.getDishId());
        if (dish == null) {
            return success(List.of());
        }
        
        List<StepDO> list = stepService.getStepListByDishId(reqVO.getDishId());
        
        // 一次性查詢所有步驟對應的菜品名稱
        Map<Long, DishDO> dishMap = dishService.getDishMap(List.of(reqVO.getDishId()));
        
        return success(list.stream()
                .map(step -> buildStepRespVO(step, dishMap.get(step.getDishId())))
                .collect(Collectors.toList()));
    }

    /**
     * 構建烹飪步驟響應 VO，添加圖片URL
     *
     * @param step 步驟DO
     * @return 步驟響應 VO
     */
    // 在 buildStepRespVO 方法中使用 StepAppConvert
    private StepRespVO buildStepRespVO(StepDO step) {
        return StepAppConvert.INSTANCE.convert(step);
    }
    
    /**
     * 構建烹飪步驟響應 VO，添加圖片URL和菜品名稱
     *
     * @param step 步驟DO
     * @param dish 菜品DO
     * @return 步驟響應 VO
     */
    private StepRespVO buildStepRespVO(StepDO step, DishDO dish) {
        if (step == null) {
            return null;
        }
        
        StepRespVO respVO = StepAppConvert.INSTANCE.convert(step);
        
        // 設置圖片URL
        if (respVO != null && respVO.getImageName() != null) {
            respVO.setImageUrl("/api/cooking/file/step/" + respVO.getImageName());
        }
        
        // 設置菜品名稱
        if (respVO != null && dish != null) {
            respVO.setDishName(dish.getName());
        }
        
        return respVO;
    }
}