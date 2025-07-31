package cn.iocoder.yudao.module.cooking.controller.app.ingredient.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "用戶 APP - 配料 Response VO")
@Data
public class IngredientRespVO {

    @Schema(description = "配料編號", required = true, example = "1024")
    private Long id;

    @Schema(description = "菜品編號", required = true, example = "1024")
    private Long dishId;

    @Schema(description = "配料名稱", required = true, example = "雞蛋")
    private String name;

    @Schema(description = "配料數量", required = true, example = "2個")
    private String quantity;

    @Schema(description = "創建時間", required = true)
    private LocalDateTime createTime;
    
    @Schema(description = "菜品名稱")
    private String dishName;

}