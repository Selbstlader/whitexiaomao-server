package cn.iocoder.yudao.module.douban.controller.admin.movie.vo;

import cn.iocoder.yudao.module.douban.controller.admin.comment.vo.CommentRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Schema(description = "管理后台 - 豆瓣电影详情 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MovieDetailRespVO extends MovieRespVO {

    @Schema(description = "电影评论列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<CommentRespVO> comments;

    @Schema(description = "平均评分", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double averageRating;

}