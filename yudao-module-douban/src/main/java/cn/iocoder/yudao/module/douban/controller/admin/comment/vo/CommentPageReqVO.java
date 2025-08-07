package cn.iocoder.yudao.module.douban.controller.admin.comment.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 豆瓣评论分页 Request VO
 */
@Schema(description = "管理后台 - 豆瓣评论分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CommentPageReqVO extends PageParam {

    @Schema(description = "电影编号", example = "movie-2363506")
    private String movieId;

    @Schema(description = "评论用户", example = "张三")
    private String people;

    @Schema(description = "评分", example = "4")
    private Integer star;

    @Schema(description = "评论内容", example = "很好看")
    private String content;

    @Schema(description = "最低有用数", example = "10")
    private Integer minUsefulNum;

}