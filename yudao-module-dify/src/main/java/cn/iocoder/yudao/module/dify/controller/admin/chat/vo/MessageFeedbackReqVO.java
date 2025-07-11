package cn.iocoder.yudao.module.dify.controller.admin.chat.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 消息反馈请求 VO
 *
 * @author 芋道源码
 */
@Schema(description = "管理后台 - 消息反馈请求 VO")
@Data
public class MessageFeedbackReqVO {

    @Schema(description = "消息 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "msg-123")
    @NotBlank(message = "消息 ID 不能为空")
    private String messageId;

    @Schema(description = "用户标识（系统自动设置）", example = "client-1")
    private String user;

    @Schema(description = "评分：like 点赞，dislike 点踩，null 取消评分", example = "like")
    private String rating;

}
