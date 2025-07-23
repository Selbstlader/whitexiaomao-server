package cn.iocoder.yudao.module.cooking.controller.admin.tip;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.cooking.controller.admin.tip.vo.*;
import cn.iocoder.yudao.module.cooking.convert.tip.TipConvert;
import cn.iocoder.yudao.module.cooking.dal.dataobject.TipDO;
import cn.iocoder.yudao.module.cooking.service.dish.DishService;
import cn.iocoder.yudao.module.cooking.service.tip.TipService;
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
 * 管理後台 - 烹飪小貼士
 */
@Tag(name = "管理後台 - 烹飪小貼士")
@RestController
@RequestMapping("/cooking/tip")
@Validated
public class TipController {

    @Resource
    private TipService tipService;

    @Resource
    private DishService dishService;

    @PostMapping("/create")
    @Operation(summary = "創建烹飪小貼士")
    @PreAuthorize("@ss.hasPermission('cooking:tip:create')")
    public CommonResult<Long> createTip(@Valid @RequestBody TipCreateReqVO createReqVO) {
        return success(tipService.createTip(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新烹飪小貼士")
    @PreAuthorize("@ss.hasPermission('cooking:tip:update')")
    public CommonResult<Boolean> updateTip(@Valid @RequestBody TipUpdateReqVO updateReqVO) {
        tipService.updateTip(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "刪除烹飪小貼士")
    @Parameter(name = "id", description = "編號", required = true)
    @PreAuthorize("@ss.hasPermission('cooking:tip:delete')")
    public CommonResult<Boolean> deleteTip(@RequestParam("id") Long id) {
        tipService.deleteTip(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "獲得烹飪小貼士")
    @Parameter(name = "id", description = "編號", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cooking:tip:query')")
    public CommonResult<TipRespVO> getTip(@RequestParam("id") Long id) {
        TipDO tip = tipService.getTip(id);
        return success(TipConvert.INSTANCE.convert(tip));
    }

    @GetMapping("/list")
    @Operation(summary = "獲得指定菜品的烹飪小貼士列表")
    @PreAuthorize("@ss.hasPermission('cooking:tip:query')")
    public CommonResult<List<TipRespVO>> getTipListByDishId(@Valid TipListReqVO reqVO) {
        // 校驗菜品是否存在
        if (dishService.getDish(reqVO.getDishId()) == null) {
            return success(List.of());
        }
        List<TipDO> list = tipService.getTipListByDishId(reqVO.getDishId());
        return success(TipConvert.INSTANCE.convertList(list));
    }

    @PostMapping("/batch-create")
    @Operation(summary = "批量創建烹飪小貼士")
    @PreAuthorize("@ss.hasPermission('cooking:tip:create')")
    public CommonResult<List<Long>> batchCreateTip(@Valid @RequestBody TipBatchCreateReqVO batchCreateReqVO) {
        return success(tipService.batchCreateTip(batchCreateReqVO));
    }

} 