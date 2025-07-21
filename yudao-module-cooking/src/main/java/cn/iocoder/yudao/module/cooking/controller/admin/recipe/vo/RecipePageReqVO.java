package cn.iocoder.yudao.module.cooking.controller.admin.recipe.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 菜谱分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RecipePageReqVO extends PageParam {

    @Schema(description = "菜谱名称", example = "宫保鸡丁")
    private String name;

    @Schema(description = "菜系分类ID", example = "1")
    private Long categoryId;

    @Schema(description = "难度等级", example = "2")
    private Integer difficulty;

    @Schema(description = "状态", example = "1")
    private Integer status;

    @Schema(description = "创建者用户ID", example = "1024")
    private Long creatorId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
