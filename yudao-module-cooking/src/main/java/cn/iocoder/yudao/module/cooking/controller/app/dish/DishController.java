package cn.iocoder.yudao.module.cooking.controller.app.dish;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.cooking.controller.app.dish.vo.*;
import cn.iocoder.yudao.module.cooking.dal.dataobject.DishDO;
import cn.iocoder.yudao.module.cooking.convert.dish.DishAppConvert;
import cn.iocoder.yudao.module.cooking.service.dish.DishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Collections;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 用户 App - 菜品
 */
@Tag(name = "用户 App - 菜品")
@RestController("appDishController")
@RequestMapping("/cooking/dish")
@Validated
public class DishController {

    @Resource
    private DishService dishService;

    @GetMapping("/get")
    @Operation(summary = "获得菜品详情")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<DishRespVO> getDish(@RequestParam("id") Long id) {
        DishDO dish = dishService.getDish(id);
        return success(DishAppConvert.INSTANCE.convert(dish));
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "获得所有菜品简略列表", description = "主要用於前端的下拉選項")
    public CommonResult<List<DishSimpleRespVO>> getSimpleDishList() {
        List<DishDO> list = dishService.getDishList(Collections.emptyList());
        return success(DishAppConvert.INSTANCE.convertList02(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得菜品分页")
    public CommonResult<PageResult<DishRespVO>> getDishPage(@Valid DishPageReqVO pageVO) {
        // 将 app 的 PageReqVO 转换为 admin 的 PageReqVO
        cn.iocoder.yudao.module.cooking.controller.admin.dish.vo.DishPageReqVO adminPageVO = 
            new cn.iocoder.yudao.module.cooking.controller.admin.dish.vo.DishPageReqVO();
        adminPageVO.setPageNo(pageVO.getPageNo());
        adminPageVO.setPageSize(pageVO.getPageSize());
        adminPageVO.setName(pageVO.getName());
        adminPageVO.setCategoryId(pageVO.getCategoryId());
        adminPageVO.setDifficulty(pageVO.getDifficulty());
        
        PageResult<DishDO> pageResult = dishService.getDishPage(adminPageVO);
        return success(DishAppConvert.INSTANCE.convertPage(pageResult));
    }

    // 可以根据需要添加其他 app 端特有的接口
    // 例如：热门菜品、推荐菜品、按分类查询等
}