package cn.iocoder.yudao.module.cooking.controller.admin.ingredient.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import jakarta.validation.constraints.NotNull;

/**
 * 配料更新 Request VO
 *
 * @author 芋道源碼
 */
@Schema(description = "管理後台 - 配料更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class IngredientUpdateReqVO extends IngredientBaseVO {

    @Schema(description = "配料編號", required = true, example = "1024")
    @NotNull(message = "配料編號不能為空")
    private Long id;

} 