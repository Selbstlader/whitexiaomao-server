package cn.iocoder.yudao.module.douban.controller.admin.movie.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@Schema(description = "管理后台 - 豆瓣电影分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MoviePageReqVO extends PageParam {

    @Schema(description = "电影名称", example = "肖申克的救赎")
    private String movieTitle;

    @Schema(description = "发布年份", example = "1994")
    private Integer releaseDate;

    @Schema(description = "出品国家", example = "美国")
    private String country;

    @Schema(description = "语言", example = "英语")
    private String language;

    @Schema(description = "最低评分", example = "8.0")
    private BigDecimal minRating;

    @Schema(description = "最高评分", example = "10.0")
    private BigDecimal maxRating;

    @Schema(description = "导演", example = "弗兰克·德拉邦特")
    private String directedBy;

    @Schema(description = "类型", example = "剧情")
    private String genre;

}