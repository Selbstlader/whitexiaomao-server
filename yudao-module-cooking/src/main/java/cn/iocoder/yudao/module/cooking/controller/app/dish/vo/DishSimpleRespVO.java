package cn.iocoder.yudao.module.cooking.controller.app.dish.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "移动端 - 菜品精簡 Response VO")
@Data
public class DishSimpleRespVO {

    @Schema(description = "菜品編號", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "菜品名稱", requiredMode = Schema.RequiredMode.REQUIRED, example = "宮保雞丁")
    private String name;

    @Schema(description = "分類編號", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long categoryId;

    @Schema(description = "難度等級", requiredMode = Schema.RequiredMode.REQUIRED, example = "3")
    private Integer difficulty;

}