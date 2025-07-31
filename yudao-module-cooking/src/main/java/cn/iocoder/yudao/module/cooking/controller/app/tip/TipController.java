package cn.iocoder.yudao.module.cooking.controller.app.tip;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.cooking.controller.app.tip.vo.*;
import cn.iocoder.yudao.module.cooking.convert.tip.TipAppConvert;
import cn.iocoder.yudao.module.cooking.dal.dataobject.TipDO;
import cn.iocoder.yudao.module.cooking.service.dish.DishService;
import cn.iocoder.yudao.module.cooking.service.tip.TipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 用戶 APP - 烹飪小貼士
 */
@Tag(name = "用戶 APP - 烹飪小貼士")
@RestController("appTipController")
@RequestMapping("/cooking/tip")
@Validated
public class TipController {

    @Resource
    private TipService tipService;

    @Resource
    private DishService dishService;

    @GetMapping("/get")
    @Operation(summary = "獲得烹飪小貼士")
    @Parameter(name = "id", description = "編號", required = true, example = "1024")
    // 修改返回类型转换
    public CommonResult<TipRespVO> getTip(@RequestParam("id") Long id) {
        TipDO tip = tipService.getTip(id);
        return success(TipAppConvert.INSTANCE.convert(tip));
    }

    @GetMapping("/list")
    @Operation(summary = "獲得指定菜品的烹飪小貼士列表")
    public CommonResult<List<TipRespVO>> getTipListByDishId(@Valid TipListReqVO reqVO) {
        // 校驗菜品是否存在
        if (dishService.getDish(reqVO.getDishId()) == null) {
            return success(List.of());
        }
        List<TipDO> list = tipService.getTipListByDishId(reqVO.getDishId());
        return success(TipAppConvert.INSTANCE.convertList(list));
    }

}