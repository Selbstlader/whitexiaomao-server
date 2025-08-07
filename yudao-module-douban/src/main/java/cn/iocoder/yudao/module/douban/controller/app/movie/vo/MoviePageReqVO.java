package cn.iocoder.yudao.module.douban.controller.app.movie.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@Schema(description = "移动端 - 电影分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MoviePageReqVO extends PageParam {

    @Schema(description = "电影标题，模糊匹配", example = "肖申克")
    private String movieTitle;

    @Schema(description = "导演", example = "弗兰克·德拉邦特")
    private String directedBy;

    @Schema(description = "类型", example = "剧情")
    private String genre;

    @Schema(description = "最低评分", example = "8.0")
    private BigDecimal minRating;

}