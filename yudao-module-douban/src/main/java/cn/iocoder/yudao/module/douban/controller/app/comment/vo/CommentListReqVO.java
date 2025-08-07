package cn.iocoder.yudao.module.douban.controller.app.comment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "移动端 - 豆瓣评论列表 Request VO")
@Data
public class CommentListReqVO {

    @Schema(description = "电影编号", required = true, example = "movie-2363506")
    private String movieId;

    @Schema(description = "评论用户", example = "张三")
    private String people;

    @Schema(description = "评分", example = "4")
    private Integer star;

}