package cn.iocoder.yudao.module.cooking.controller.app.dish.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "移动端 - 菜品 Response VO")
@Data
public class DishRespVO {

    @Schema(description = "菜品編號", required = true, example = "1024")
    private Long id;

    @Schema(description = "分類編號", required = true, example = "1")
    private Long categoryId;

    @Schema(description = "菜品名稱", required = true, example = "清蒸鱸魚")
    private String name;

    @Schema(description = "菜品描述", example = "鮮美可口的清蒸鱸魚")
    private String description;

    @Schema(description = "圖片名稱", example = "food_1.jpg")
    private String imageName;

    @Schema(description = "難度等級", required = true, example = "3")
    private Integer difficulty;

    @Schema(description = "烹飪時間（分鐘）", required = true, example = "30")
    private Integer cookingTime;

    @Schema(description = "分類名稱", example = "素菜")
    private String categoryName;

    @Schema(description = "創建時間", required = true)
    private LocalDateTime createTime;

}