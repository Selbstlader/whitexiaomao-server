package cn.iocoder.yudao.module.cooking.controller.admin.recipe.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import jakarta.validation.constraints.NotNull;

@Schema(description = "管理后台 - 菜谱更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RecipeUpdateReqVO extends RecipeBaseVO {

    @Schema(description = "菜谱编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "菜谱编号不能为空")
    private Long id;

}
