package cn.iocoder.yudao.module.cooking.controller.admin.ingredient.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * 配料 Base VO，提供給添加、修改、詳情使用
 */
@Data
public class IngredientBaseVO {

    @Schema(description = "菜品編號", required = true, example = "1024")
    @NotNull(message = "菜品編號不能為空")
    private Long dishId;

    @Schema(description = "配料名稱", required = true, example = "鹽")
    @NotEmpty(message = "配料名稱不能為空")
    private String name;

    @Schema(description = "配料份量", required = true, example = "少許")
    @NotEmpty(message = "配料份量不能為空")
    private String amount;

} 