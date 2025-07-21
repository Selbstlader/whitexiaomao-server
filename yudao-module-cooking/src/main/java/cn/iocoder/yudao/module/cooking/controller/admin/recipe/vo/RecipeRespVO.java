package cn.iocoder.yudao.module.cooking.controller.admin.recipe.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 菜谱 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RecipeRespVO extends RecipeBaseVO {

    @Schema(description = "菜谱编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "总时间（分钟）", example = "35")
    private Integer totalTime;

    @Schema(description = "纤维（克）", example = "5.2")
    private BigDecimal fiber;

    @Schema(description = "糖分（克）", example = "8.1")
    private BigDecimal sugar;

    @Schema(description = "钠（毫克）", example = "800.5")
    private BigDecimal sodium;

    @Schema(description = "状态", example = "1")
    private Integer status;

    @Schema(description = "创建者用户ID", example = "1024")
    private Long creatorId;

    @Schema(description = "浏览次数", example = "100")
    private Integer viewCount;

    @Schema(description = "收藏次数", example = "50")
    private Integer favoriteCount;

    @Schema(description = "评分（1-5星）", example = "4.5")
    private BigDecimal rating;

    @Schema(description = "评分次数", example = "20")
    private Integer ratingCount;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
