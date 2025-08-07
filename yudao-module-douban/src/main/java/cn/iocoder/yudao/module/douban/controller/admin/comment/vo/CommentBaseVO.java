package cn.iocoder.yudao.module.douban.controller.admin.comment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * 豆瓣评论 Base VO，提供给添加、修改、详情使用
 */
@Data
public class CommentBaseVO {

    @Schema(description = "电影编号", required = true, example = "movie-2363506")
    @NotEmpty(message = "电影编号不能为空")
    private String movieId;

    @Schema(description = "评论用户", required = true, example = "张三")
    @NotEmpty(message = "评论用户不能为空")
    private String people;

    @Schema(description = "评分", required = true, example = "4")
    @NotNull(message = "评分不能为空")
    @Min(value = 1, message = "评分最低为1星")
    @Max(value = 5, message = "评分最高为5星")
    private Integer star;

    @Schema(description = "评论内容", required = true, example = "这部电影很好看")
    @NotEmpty(message = "评论内容不能为空")
    private String content;

}