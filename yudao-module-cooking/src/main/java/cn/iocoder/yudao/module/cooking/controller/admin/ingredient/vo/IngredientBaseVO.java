package cn.iocoder.yudao.module.cooking.controller.admin.ingredient.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * 食材 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class IngredientBaseVO {

    @Schema(description = "食材名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "鸡胸肉")
    @NotBlank(message = "食材名称不能为空")
    @Size(max = 100, message = "食材名称长度不能超过100个字符")
    private String name;

    @Schema(description = "食材描述", example = "优质蛋白质来源，低脂肪")
    @Size(max = 500, message = "食材描述长度不能超过500个字符")
    private String description;

    @Schema(description = "食材图片", example = "https://example.com/chicken.jpg")
    private String image;

    @Schema(description = "食材分类ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "食材分类不能为空")
    private Long categoryId;

    @Schema(description = "单位", requiredMode = Schema.RequiredMode.REQUIRED, example = "克")
    @NotBlank(message = "单位不能为空")
    @Size(max = 10, message = "单位长度不能超过10个字符")
    private String unit;

    @Schema(description = "每100g热量（卡路里）", example = "165.0")
    @DecimalMin(value = "0", message = "热量不能为负数")
    private BigDecimal caloriesPer100g;

    @Schema(description = "每100g蛋白质（克）", example = "31.0")
    @DecimalMin(value = "0", message = "蛋白质不能为负数")
    private BigDecimal proteinPer100g;

    @Schema(description = "每100g脂肪（克）", example = "3.6")
    @DecimalMin(value = "0", message = "脂肪不能为负数")
    private BigDecimal fatPer100g;

    @Schema(description = "每100g碳水化合物（克）", example = "0.0")
    @DecimalMin(value = "0", message = "碳水化合物不能为负数")
    private BigDecimal carbohydratesPer100g;

    @Schema(description = "每100g纤维（克）", example = "0.0")
    @DecimalMin(value = "0", message = "纤维不能为负数")
    private BigDecimal fiberPer100g;

    @Schema(description = "每100g糖分（克）", example = "0.0")
    @DecimalMin(value = "0", message = "糖分不能为负数")
    private BigDecimal sugarPer100g;

    @Schema(description = "每100g钠（毫克）", example = "74.0")
    @DecimalMin(value = "0", message = "钠不能为负数")
    private BigDecimal sodiumPer100g;

    @Schema(description = "季节性（JSON数组）", example = "[\"春\",\"夏\",\"秋\",\"冬\"]")
    private String seasons;

    @Schema(description = "存储建议", example = "冷藏保存，2-3天内食用")
    @Size(max = 200, message = "存储建议长度不能超过200个字符")
    private String storageAdvice;

    @Schema(description = "保质期（天）", example = "3")
    @Min(value = 1, message = "保质期至少为1天")
    private Integer shelfLife;

    @Schema(description = "备注", example = "适合减脂人群")
    @Size(max = 200, message = "备注长度不能超过200个字符")
    private String remark;

}
