package cn.iocoder.yudao.module.douban.controller.admin.movie.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 豆瓣电影 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class MovieBaseVO {

    @Schema(description = "电影唯一ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
    @NotBlank(message = "电影唯一ID不能为空")
    private String movieId;

    @Schema(description = "电影名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "肖申克的救赎")
    @NotBlank(message = "电影名称不能为空")
    private String movieTitle;

    @Schema(description = "发布年份", example = "1994")
    private Integer releaseDate;

    @Schema(description = "导演（JSON格式）", example = "[\"弗兰克·德拉邦特\"]")
    private String directedBy;

    @Schema(description = "主演（JSON格式）", example = "[\"蒂姆·罗宾斯\", \"摩根·弗里曼\"]")
    private String starring;

    @Schema(description = "电影类型（JSON格式）", example = "[\"剧情\", \"犯罪\"]")
    private String genre;

    @Schema(description = "电影时长（分钟）", example = "142")
    private Integer runtime;

    @Schema(description = "出品国家", example = "美国")
    private String country;

    @Schema(description = "语言", example = "英语")
    private String language;

    @Schema(description = "总评分", example = "9.7")
    private BigDecimal ratingNum;

    @Schema(description = "评分人数", example = "2000000")
    private Integer voteNum;

    @Schema(description = "5星百分比", example = "85.50")
    private BigDecimal ratingPerStars5;

    @Schema(description = "4星百分比", example = "12.30")
    private BigDecimal ratingPerStars4;

    @Schema(description = "3星百分比", example = "1.80")
    private BigDecimal ratingPerStars3;

    @Schema(description = "2星百分比", example = "0.30")
    private BigDecimal ratingPerStars2;

    @Schema(description = "1星百分比", example = "0.10")
    private BigDecimal ratingPerStars1;

    @Schema(description = "电影简介")
    private String intro;

    @Schema(description = "短评数", example = "500000")
    private Integer commentNum;

    @Schema(description = "提问数", example = "1000")
    private Integer questionNum;
}