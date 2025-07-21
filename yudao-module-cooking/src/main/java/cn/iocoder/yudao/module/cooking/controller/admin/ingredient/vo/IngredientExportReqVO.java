package cn.iocoder.yudao.module.cooking.controller.admin.ingredient.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 食材导出 Request VO")
@Data
public class IngredientExportReqVO {

    @Schema(description = "食材名称", example = "鸡胸肉")
    private String name;

    @Schema(description = "食材分类ID", example = "1")
    private Long categoryId;

    @Schema(description = "状态", example = "1")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
