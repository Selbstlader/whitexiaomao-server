package cn.iocoder.yudao.module.cooking.controller.admin.recipe.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 菜谱 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class RecipeExcelVO {

    @ExcelProperty("菜谱编号")
    private Long id;

    @ExcelProperty("菜谱名称")
    private String name;

    @ExcelProperty("菜谱描述")
    private String description;

    @ExcelProperty("菜系分类ID")
    private Long categoryId;

    @ExcelProperty(value = "难度等级", converter = DictConvert.class)
    @DictFormat("cooking_recipe_difficulty")
    private Integer difficulty;

    @ExcelProperty("准备时间（分钟）")
    private Integer prepTime;

    @ExcelProperty("烹饪时间（分钟）")
    private Integer cookTime;

    @ExcelProperty("总时间（分钟）")
    private Integer totalTime;

    @ExcelProperty("份数")
    private Integer servings;

    @ExcelProperty("热量（卡路里）")
    private BigDecimal calories;

    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("cooking_recipe_status")
    private Integer status;

    @ExcelProperty("创建者用户ID")
    private Long creatorId;

    @ExcelProperty("浏览次数")
    private Integer viewCount;

    @ExcelProperty("收藏次数")
    private Integer favoriteCount;

    @ExcelProperty("评分（1-5星）")
    private BigDecimal rating;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
