package cn.iocoder.yudao.module.cooking.controller.admin.dish.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * 菜品 Base VO，提供給添加、修改、詳情使用
 */
@Data
public class DishBaseVO {

    @Schema(description = "分類編號", required = true, example = "1")
    @NotNull(message = "分類編號不能為空")
    private Long categoryId;

    @Schema(description = "菜品名稱", required = true, example = "清蒸鱸魚")
    @NotEmpty(message = "菜品名稱不能為空")
    private String name;

    @Schema(description = "菜品描述", example = "鮮美可口的清蒸鱸魚")
    private String description;

    @Schema(description = "圖片名稱", example = "food_1.jpg")
    private String imageName;

    @Schema(description = "難度等級", required = true, example = "3")
    @NotNull(message = "難度等級不能為空")
    private Integer difficulty;

    @Schema(description = "烹飪時間（分鐘）", required = true, example = "30")
    @NotNull(message = "烹飪時間不能為空")
    private Integer cookingTime;

} 