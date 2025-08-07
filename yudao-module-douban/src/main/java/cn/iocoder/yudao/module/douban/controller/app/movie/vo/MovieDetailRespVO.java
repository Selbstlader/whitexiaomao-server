package cn.iocoder.yudao.module.douban.controller.app.movie.vo;

import cn.iocoder.yudao.module.douban.controller.app.comment.vo.CommentRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "移动端 - 电影详情 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MovieDetailRespVO extends MovieRespVO {

    @Schema(description = "电影评论列表")
    private List<CommentRespVO> comments;

    @Schema(description = "平均评分", example = "9.2")
    private BigDecimal averageRating;

    @Schema(description = "评论总数", example = "1000")
    private Long commentCount;

}