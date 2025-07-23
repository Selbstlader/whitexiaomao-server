package cn.iocoder.yudao.module.cooking.controller.admin.category.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import jakarta.validation.constraints.NotNull;

/**
 * 菜品分類更新 Request VO
 *
 * @author 芋道源碼
 */
@Schema(description = "管理後台 - 菜品分類更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CategoryUpdateReqVO extends CategoryBaseVO {

    @Schema(description = "分類編號", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "分類編號不能為空")
    private Long id;

} 