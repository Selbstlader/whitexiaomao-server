package cn.iocoder.yudao.module.douban.controller.admin.comment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import jakarta.validation.constraints.NotNull;

/**
 * 豆瓣评论更新 Request VO
 */
@Schema(description = "管理后台 - 豆瓣评论更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CommentUpdateReqVO extends CommentBaseVO {

    @Schema(description = "评论编号", required = true, example = "1024")
    @NotNull(message = "评论编号不能为空")
    private Long id;

}