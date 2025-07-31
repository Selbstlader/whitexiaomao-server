package cn.iocoder.yudao.module.cooking.controller.admin.step;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.cooking.controller.admin.step.vo.*;
import cn.iocoder.yudao.module.cooking.convert.step.StepConvert;
import cn.iocoder.yudao.module.cooking.dal.dataobject.DishDO;
import cn.iocoder.yudao.module.cooking.dal.dataobject.StepDO;
import cn.iocoder.yudao.module.cooking.service.dish.DishService;
import cn.iocoder.yudao.module.cooking.service.step.StepService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 管理後台 - 烹飪步驟
 */
@Tag(name = "管理後台 - 烹飪步驟")
@RestController
@RequestMapping("/cooking/step")
public class StepController {

    @Resource
    private StepService stepService;

    @Resource
    private DishService dishService;

    @PostMapping("/create")
    @Operation(summary = "創建烹飪步驟")
    @PreAuthorize("@ss.hasPermission('cooking:step:create')")
    public CommonResult<Long> createStep(@Valid StepCreateReqVO createReqVO) {
        return success(stepService.createStep(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新烹飪步驟")
    @PreAuthorize("@ss.hasPermission('cooking:step:update')")
    public CommonResult<Boolean> updateStep(@Valid StepUpdateReqVO updateReqVO) {
        stepService.updateStep(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "刪除烹飪步驟")
    @Parameter(name = "id", description = "編號", required = true)
    @PreAuthorize("@ss.hasPermission('cooking:step:delete')")
    public CommonResult<Boolean> deleteStep(@RequestParam("id") Long id) {
        stepService.deleteStep(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "獲得烹飪步驟")
    @Parameter(name = "id", description = "編號", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cooking:step:query')")
    public CommonResult<StepRespVO> getStep(@RequestParam("id") Long id) {
        StepDO step = stepService.getStep(id);
        return success(buildStepRespVO(step));
    }

    @GetMapping("/list")
    @Operation(summary = "獲得指定菜品的烹飪步驟列表")
    @PreAuthorize("@ss.hasPermission('cooking:step:query')")
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

    @PostMapping("/batch-create")
    @Operation(summary = "批量創建烹飪步驟")
    @PreAuthorize("@ss.hasPermission('cooking:step:create')")
    public CommonResult<List<Long>> batchCreateStep(@Valid @RequestBody StepBatchCreateReqVO batchCreateReqVO) {
        return success(stepService.batchCreateStep(batchCreateReqVO));
    }

    /**
     * 構建烹飪步驟響應 VO，添加圖片URL
     *
     * @param step 步驟DO
     * @return 步驟響應 VO
     */
    private StepRespVO buildStepRespVO(StepDO step) {
        if (step == null) {
            return null;
        }
        
        // 查詢菜品信息
        DishDO dish = dishService.getDish(step.getDishId());
        return buildStepRespVO(step, dish);
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
        
        StepRespVO respVO = StepConvert.INSTANCE.convert(step);
        
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