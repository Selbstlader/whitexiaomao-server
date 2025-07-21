package cn.iocoder.yudao.module.cooking.controller.app.vo.recipe;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "用户 APP - 菜谱分页 Request VO")
@Data
@ToString(callSuper = true)
public class RecipePageReqVO {

    @Schema(description = "菜谱名称", example = "红烧肉")
    private String name;

    @Schema(description = "分类编号", example = "1")
    private Long categoryId;

    @Schema(description = "难度等级", example = "1")
    private Integer difficulty;

    @Schema(description = "烹饪时间", example = "30")
    private Integer cookingTime;

    @Schema(description = "菜系", example = "川菜")
    private String cuisine;

    @Schema(description = "创建时间", example = "2023-07-01 00:00:00")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime createTime;
    
    @Schema(description = "页码", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer pageNo;

    @Schema(description = "每页条数", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Integer pageSize;
    
} 