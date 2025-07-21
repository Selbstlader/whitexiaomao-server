package cn.iocoder.yudao.module.cooking.controller.admin.recipe.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * 菜谱 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class RecipeBaseVO {

    @Schema(description = "菜谱名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "宫保鸡丁")
    @NotBlank(message = "菜谱名称不能为空")
    @Size(max = 100, message = "菜谱名称长度不能超过100个字符")
    private String name;

    @Schema(description = "菜谱描述", example = "经典川菜，酸甜可口")
    @Size(max = 500, message = "菜谱描述长度不能超过500个字符")
    private String description;

    @Schema(description = "菜谱封面图片", example = "https://example.com/image.jpg")
    private String coverImage;

    @Schema(description = "菜系分类ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "菜系分类不能为空")
    private Long categoryId;

    @Schema(description = "难度等级", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "难度等级不能为空")
    @Min(value = 1, message = "难度等级最小为1")
    @Max(value = 4, message = "难度等级最大为4")
    private Integer difficulty;

    @Schema(description = "准备时间（分钟）", requiredMode = Schema.RequiredMode.REQUIRED, example = "15")
    @NotNull(message = "准备时间不能为空")
    @Min(value = 0, message = "准备时间不能为负数")
    private Integer prepTime;

    @Schema(description = "烹饪时间（分钟）", requiredMode = Schema.RequiredMode.REQUIRED, example = "20")
    @NotNull(message = "烹饪时间不能为空")
    @Min(value = 0, message = "烹饪时间不能为负数")
    private Integer cookTime;

    @Schema(description = "份数", requiredMode = Schema.RequiredMode.REQUIRED, example = "4")
    @NotNull(message = "份数不能为空")
    @Min(value = 1, message = "份数最少为1")
    private Integer servings;

    @Schema(description = "热量（卡路里）", example = "350.5")
    @DecimalMin(value = "0", message = "热量不能为负数")
    private BigDecimal calories;

    @Schema(description = "蛋白质（克）", example = "25.3")
    @DecimalMin(value = "0", message = "蛋白质不能为负数")
    private BigDecimal protein;

    @Schema(description = "脂肪（克）", example = "15.2")
    @DecimalMin(value = "0", message = "脂肪不能为负数")
    private BigDecimal fat;

    @Schema(description = "碳水化合物（克）", example = "30.1")
    @DecimalMin(value = "0", message = "碳水化合物不能为负数")
    private BigDecimal carbohydrates;

    @Schema(description = "标签（JSON数组）", example = "[\"川菜\",\"下饭菜\"]")
    private String tags;

    @Schema(description = "备注", example = "适合晚餐")
    @Size(max = 200, message = "备注长度不能超过200个字符")
    private String remark;

}
