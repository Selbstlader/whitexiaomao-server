package cn.iocoder.yudao.module.cooking.controller.app.vo.recipe;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "用户 APP - 菜谱详情 Response VO")
@Data
@ToString(callSuper = true)
public class RecipeDetailVO extends RecipeVO {

    @Schema(description = "准备时间(分钟)", example = "15")
    private Integer prepTime;

    @Schema(description = "烹饪小贴士", example = "煮之前先腌制30分钟，味道更佳")
    private String tips;

    @Schema(description = "营养信息", example = "热量：250大卡，蛋白质：15克")
    private String nutrition;

    @Schema(description = "用户是否已收藏", required = true, example = "true")
    private Boolean favorited;

    @Schema(description = "食材列表")
    private List<RecipeIngredientVO> ingredients;

    @Schema(description = "步骤列表")
    private List<RecipeStepVO> steps;

    @Schema(description = "更新时间", required = true)
    private LocalDateTime updateTime;

    @Schema(description = "用户 APP - 菜谱食材 Response VO")
    @Data
    public static class RecipeIngredientVO {

        @Schema(description = "食材编号", required = true, example = "1")
        private Long id;

        @Schema(description = "食材名称", required = true, example = "五花肉")
        private String name;

        @Schema(description = "数量", required = true, example = "500")
        private String amount;

        @Schema(description = "单位", required = true, example = "克")
        private String unit;
    }

    @Schema(description = "用户 APP - 菜谱步骤 Response VO")
    @Data
    public static class RecipeStepVO {

        @Schema(description = "步骤序号", required = true, example = "1")
        private Integer stepNumber;

        @Schema(description = "步骤描述", required = true, example = "将五花肉切成方块")
        private String description;

        @Schema(description = "步骤图片", example = "https://www.example.com/images/step1.jpg")
        private String image;
    }

} 