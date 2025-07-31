package cn.iocoder.yudao.module.cooking.controller.app.ingredient.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Schema(description = "用戶 APP - 配料列表查詢 Request VO")
@Data
public class IngredientListReqVO {

    @Schema(description = "菜品編號", required = true, example = "1024")
    @NotNull(message = "菜品編號不能為空")
    private Long dishId;

}