package cn.iocoder.yudao.module.cooking.controller.app.vo.ingredient;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Schema(description = "用户 APP - 食材信息 Response VO")
@Data
@ToString(callSuper = true)
public class IngredientRespVO {

    @Schema(description = "食材编号", required = true, example = "1")
    private Long id;

    @Schema(description = "食材名称", required = true, example = "土豆")
    private String name;

    @Schema(description = "食材描述", example = "富含淀粉的块茎类蔬菜")
    private String description;

    @Schema(description = "食材图片", example = "https://www.example.com/images/potato.jpg")
    private String image;

    @Schema(description = "食材分类ID", required = true, example = "1")
    private Long categoryId;
    
    @Schema(description = "食材分类名称", example = "蔬菜类")
    private String categoryName;

    @Schema(description = "单位", example = "个")
    private String unit;

    @Schema(description = "每100g热量（卡路里）", example = "77")
    private BigDecimal caloriesPer100g;

    @Schema(description = "每100g蛋白质（克）", example = "2.0")
    private BigDecimal proteinPer100g;

    @Schema(description = "每100g脂肪（克）", example = "0.1")
    private BigDecimal fatPer100g;

    @Schema(description = "每100g碳水化合物（克）", example = "17.5")
    private BigDecimal carbohydratesPer100g;

    @Schema(description = "每100g纤维（克）", example = "2.2")
    private BigDecimal fiberPer100g;

    @Schema(description = "每100g糖分（克）", example = "0.8")
    private BigDecimal sugarPer100g;

    @Schema(description = "每100g钠（毫克）", example = "6")
    private BigDecimal sodiumPer100g;

    @Schema(description = "季节性", example = "['春','秋']")
    private String seasons;

    @Schema(description = "存储建议", example = "阴凉干燥处保存")
    private String storageAdvice;

    @Schema(description = "保质期（天）", example = "14")
    private Integer shelfLife;

    @Schema(description = "状态（0禁用 1启用）", required = true, example = "1")
    private Integer status;

    @Schema(description = "备注", example = "适合炒、炖、烤等多种烹饪方式")
    private String remark;
} 