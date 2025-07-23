package cn.iocoder.yudao.module.cooking.controller.admin.category.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 菜品分類 Response VO
 *
 * @author 芋道源碼
 */
@Schema(description = "管理後台 - 菜品分類 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CategoryRespVO extends CategoryBaseVO {

    @Schema(description = "分類編號", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

} 