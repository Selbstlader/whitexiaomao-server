package cn.iocoder.yudao.module.cooking.controller.app.vo.recipe;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "用户 APP - 菜谱信息 Response VO")
@Data
@ToString(callSuper = true)
public class RecipeVO {

    @Schema(description = "菜谱编号", required = true, example = "1")
    private Long id;

    @Schema(description = "菜谱名称", required = true, example = "红烧肉")
    private String name;

    @Schema(description = "菜谱描述", example = "传统经典的红烧肉做法")
    private String description;

    @Schema(description = "封面图", example = "https://www.example.com/images/hongshaorou.jpg")
    private String coverImage;

    @Schema(description = "分类编号", required = true, example = "1")
    private Long categoryId;

    @Schema(description = "分类名称", required = true, example = "川菜")
    private String categoryName;

    @Schema(description = "难度等级(1简单 2一般 3困难)", required = true, example = "2")
    private Integer difficulty;

    @Schema(description = "烹饪时间(分钟)", required = true, example = "60")
    private Integer cookingTime;

    @Schema(description = "份量(人份)", required = true, example = "4")
    private Integer servings;

    @Schema(description = "浏览次数", required = true, example = "1000")
    private Integer viewCount;

    @Schema(description = "评分(1-5星)", required = true, example = "4.5")
    private Double rating;

    @Schema(description = "收藏次数", required = true, example = "100")
    private Integer favoriteCount;

    @Schema(description = "菜系", example = "川菜")
    private String cuisine;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

} 