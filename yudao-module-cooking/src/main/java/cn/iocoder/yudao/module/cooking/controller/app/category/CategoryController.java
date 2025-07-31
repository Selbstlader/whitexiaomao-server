package cn.iocoder.yudao.module.cooking.controller.app.category;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.cooking.controller.admin.category.vo.CategorySimpleRespVO;
import cn.iocoder.yudao.module.cooking.convert.category.CategoryConvert;
import cn.iocoder.yudao.module.cooking.dal.dataobject.category.CategoryDO;
import cn.iocoder.yudao.module.cooking.service.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 用户 App - 菜品分類
 */
@Tag(name = "用户 App - 菜品分類")
@RestController("appCategoryController")
@RequestMapping("/cooking/category")
@Validated
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/list-all-simple")
    @Operation(summary = "获得所有菜品分類精简列表", description = "主要用於前端的下拉選項")
    public CommonResult<List<CategorySimpleRespVO>> getSimpleCategoryList() {
        List<CategoryDO> list = categoryService.getCategoryList();
        return success(CategoryConvert.INSTANCE.convertList02(list));
    }

    // 可以根据需要添加其他 app 端需要的接口
    // 例如：获取分类详情、热门分类等
}