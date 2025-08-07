package cn.iocoder.yudao.module.douban.controller.app.movie.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "移动端 - 电影精简 Response VO")
@Data
public class MovieSimpleRespVO {

    @Schema(description = "电影编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "电影标题", requiredMode = Schema.RequiredMode.REQUIRED, example = "肖申克的救赎")
    private String movieTitle;

    @Schema(description = "导演", requiredMode = Schema.RequiredMode.REQUIRED, example = "弗兰克·德拉邦特")
    private String directedBy;

    @Schema(description = "评分", requiredMode = Schema.RequiredMode.REQUIRED, example = "9.7")
    private BigDecimal ratingNum;

}