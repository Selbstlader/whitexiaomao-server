package cn.iocoder.yudao.module.douban.controller.app.comment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "移动端 - 豆瓣评论 Response VO")
@Data
public class CommentRespVO {

    @Schema(description = "评论编号", required = true, example = "1024")
    private Long id;

    @Schema(description = "评论ID", example = "comment-123")
    private String commentId;

    @Schema(description = "电影编号", required = true, example = "movie-2363506")
    private String movieId;

    @Schema(description = "电影标题", example = "肖申克的救赎")
    private String movieTitle;

    @Schema(description = "评论用户", required = true, example = "张三")
    private String people;

    @Schema(description = "评分", required = true, example = "4")
    private Integer star;

    @Schema(description = "评论内容", required = true, example = "这部电影很好看")
    private String content;

    @Schema(description = "有用数", example = "10")
    private Integer usefulNum;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

}