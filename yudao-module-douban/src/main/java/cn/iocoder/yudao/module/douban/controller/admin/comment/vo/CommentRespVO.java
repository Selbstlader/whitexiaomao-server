package cn.iocoder.yudao.module.douban.controller.admin.comment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 豆瓣评论 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CommentRespVO extends CommentBaseVO {

    @Schema(description = "评论编号", required = true, example = "1024")
    private Long id;

    @Schema(description = "电影标题", example = "肖申克的救赎")
    private String movieTitle;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

}