package cn.iocoder.yudao.module.cooking.controller.admin.ingredient.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import jakarta.validation.constraints.NotNull;

@Schema(description = "管理后台 - 食材更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class IngredientUpdateReqVO extends IngredientBaseVO {

    @Schema(description = "食材编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "食材编号不能为空")
    private Long id;

}
