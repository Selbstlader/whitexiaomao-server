package cn.iocoder.yudao.module.cooking.controller.admin.dish.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "管理後台 - 菜品 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DishRespVO extends DishBaseVO {

    @Schema(description = "菜品編號", required = true, example = "1024")
    private Long id;

    @Schema(description = "分類名稱", example = "素菜")
    private String categoryName;

    @Schema(description = "創建時間", required = true)
    private LocalDateTime createTime;

} 