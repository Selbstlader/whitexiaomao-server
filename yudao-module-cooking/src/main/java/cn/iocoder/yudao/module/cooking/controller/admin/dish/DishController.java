package cn.iocoder.yudao.module.cooking.controller.admin.dish;

import cn.hutool.core.io.IoUtil;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX; // 新增
import cn.iocoder.yudao.module.cooking.controller.admin.dish.vo.*;
import cn.iocoder.yudao.module.cooking.convert.dish.DishConvert; // 新增
import cn.iocoder.yudao.module.cooking.dal.dataobject.DishDO; // 新增
import cn.iocoder.yudao.module.cooking.dal.dataobject.category.CategoryDO; // 新增
import cn.iocoder.yudao.module.cooking.dal.mysql.dish.DishMapper; // 新增
import cn.iocoder.yudao.module.cooking.service.category.CategoryService; // 新增
import cn.iocoder.yudao.module.cooking.service.dish.DishService;
import cn.iocoder.yudao.module.infra.service.file.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter; // 新增
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection; // 新增
import java.util.List; // 新增
import java.util.Map; // 新增
import java.util.stream.Collectors; // 新增

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 菜品")
@RestController
@RequestMapping("/cooking/dish")
@Validated
public class DishController {

    @Resource
    private DishService dishService;
    
    @Resource
    private FileService fileService;
    
    @Resource
    private CategoryService categoryService; // 新增：注入分类服务
    
    @Resource
    private DishMapper dishMapper; // 新增：注入菜品Mapper

    @PostMapping("/create")
    @Operation(summary = "创建菜品")
    @PreAuthorize("@ss.hasPermission('cooking:dish:create')")
    public CommonResult<Long> createDish(@ModelAttribute @Valid DishCreateReqVO createReqVO) throws Exception {
        // 处理图片上传
        String imageUrl = null;
        if (createReqVO.getImageFile() != null && !createReqVO.getImageFile().isEmpty()) {
            byte[] content = IoUtil.readBytes(createReqVO.getImageFile().getInputStream());
            imageUrl = fileService.createFile(
                content, 
                createReqVO.getImageFile().getOriginalFilename(),
                "cooking/dishes", // 统一目录管理
                createReqVO.getImageFile().getContentType()
            );
        }
        
        // 设置图片URL到VO（注意：这里改为存储完整URL而不是文件名）
        createReqVO.setImageName(imageUrl);
        return success(dishService.createDish(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新菜品")
    @PreAuthorize("@ss.hasPermission('cooking:dish:update')")
    public CommonResult<Boolean> updateDish(@ModelAttribute @Valid DishUpdateReqVO updateReqVO) throws Exception {
        // 处理图片上传
        String imageUrl = null;
        if (updateReqVO.getImageFile() != null && !updateReqVO.getImageFile().isEmpty()) {
            byte[] content = IoUtil.readBytes(updateReqVO.getImageFile().getInputStream());
            imageUrl = fileService.createFile(
                content, 
                updateReqVO.getImageFile().getOriginalFilename(),
                "cooking/dishes",
                updateReqVO.getImageFile().getContentType()
            );
            updateReqVO.setImageName(imageUrl);
        }
        
        dishService.updateDish(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "刪除菜品")
    @Parameter(name = "id", description = "編號", required = true)
    // @PreAuthorize("@ss.hasPermission('cooking:dish:delete')") // 注释掉权限控制
    public CommonResult<Boolean> deleteDish(@RequestParam("id") Long id) {
        dishService.deleteDish(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "獲得菜品")
    @Parameter(name = "id", description = "編號", required = true, example = "1024")
    // @PreAuthorize("@ss.hasPermission('cooking:dish:query')") // 注释掉权限控制
    public CommonResult<DishRespVO> getDish(@RequestParam("id") Long id) {
        DishDO dish = dishService.getDish(id);
        // 獲取菜品分類信息
        CategoryDO category = categoryService.getCategory(dish.getCategoryId());
        return success(DishConvert.INSTANCE.convert(dish, category));
    }

    @GetMapping("/detail")
    @Operation(summary = "獲得菜品詳情")
    @Parameter(name = "id", description = "編號", required = true, example = "1024")
    // @PreAuthorize("@ss.hasPermission('cooking:dish:query')") // 注释掉权限控制
    public CommonResult<DishDetailRespVO> getDishDetail(@RequestParam("id") Long id) {
        return success(dishService.getDishDetail(id));
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "獲得所有菜品精簡列表", description = "主要用於前端的下拉選項")
    public CommonResult<List<DishSimpleRespVO>> getSimpleDishList() {
        // 直接查詢數據庫，繞過可能有問題的 service 層
        // 添加以下代碼
        System.out.println("=== DEBUG: 嘗試獲取菜品列表 ===");
        
        // 方法一：直接查詢（如果 DishDO 的 @TableName 正確）
        List<DishDO> list = dishMapper.selectList(new LambdaQueryWrapperX<DishDO>()
                .eq(DishDO::getDeleted, false));
                
        System.out.println("=== DEBUG: 獲取到菜品數量: " + list.size() + " ===");
        return success(DishConvert.INSTANCE.convertList02(list));
    }

    @GetMapping("/page")
    @Operation(summary = "獲得菜品分頁")
    // @PreAuthorize("@ss.hasPermission('cooking:dish:query')") // 注释掉权限控制
    public CommonResult<PageResult<DishRespVO>> getDishPage(@Valid DishPageReqVO pageVO) {
        PageResult<DishDO> pageResult = dishService.getDishPage(pageVO);
        
        // 獲取分類信息
        Map<Long, CategoryDO> categoryMap = getCategoryMap(pageResult.getList().stream()
                .map(DishDO::getCategoryId).collect(Collectors.toList()));
        
        // 組裝返回結果
        PageResult<DishRespVO> voPageResult = DishConvert.INSTANCE.convertPage(pageResult, categoryMap);
        return success(voPageResult);
    }
    
    private Map<Long, CategoryDO> getCategoryMap(Collection<Long> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            return Map.of();
        }
        List<CategoryDO> categories = categoryService.getCategoryList(categoryIds);
        return categories.stream().collect(Collectors.toMap(CategoryDO::getId, category -> category));
    }
}