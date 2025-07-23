package cn.iocoder.yudao.module.cooking.controller.admin.category.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 菜品分類精簡 Response VO
 *
 * @author 芋道源碼
 */
@Schema(description = "管理後台 - 菜品分類精簡 Response VO")
@Data
public class CategorySimpleRespVO {

    @Schema(description = "分類編號", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "分類名稱", requiredMode = Schema.RequiredMode.REQUIRED, example = "川菜")
    private String name;

} 