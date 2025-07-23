package cn.iocoder.yudao.module.cooking.controller.admin.ingredient.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "管理後台 - 配料批量創建 Request VO")
@Data
public class IngredientBatchCreateReqVO {

    @Schema(description = "菜品編號", required = true, example = "1024")
    @NotNull(message = "菜品編號不能為空")
    private Long dishId;

    @Schema(description = "配料列表", required = true)
    @NotEmpty(message = "配料列表不能為空")
    @Valid
    private List<IngredientBaseInfoVO> ingredients;

    @Schema(description = "配料基本信息")
    @Data
    public static class IngredientBaseInfoVO {

        @Schema(description = "配料名稱", required = true, example = "鹽")
        @NotEmpty(message = "配料名稱不能為空")
        private String name;

        @Schema(description = "配料份量", required = true, example = "少許")
        @NotEmpty(message = "配料份量不能為空")
        private String amount;
    }
} 