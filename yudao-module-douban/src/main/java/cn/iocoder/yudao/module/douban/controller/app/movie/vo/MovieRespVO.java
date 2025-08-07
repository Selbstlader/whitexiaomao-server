package cn.iocoder.yudao.module.douban.controller.app.movie.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Schema(description = "移动端 - 电影 Response VO")
@Data
public class MovieRespVO {

    @Schema(description = "电影编号", required = true, example = "1024")
    private Long id;

    @Schema(description = "电影标题", required = true, example = "肖申克的救赎")
    private String movieTitle;

    @Schema(description = "上映日期", example = "1994-09-23")
    private LocalDate releaseDate;

    @Schema(description = "导演", example = "弗兰克·德拉邦特")
    private String directedBy;

    @Schema(description = "主演", example = "蒂姆·罗宾斯,摩根·弗里曼")
    private String starring;

    @Schema(description = "类型", example = "剧情,犯罪")
    private String genre;

    @Schema(description = "评分", example = "9.7")
    private BigDecimal ratingNum;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

}