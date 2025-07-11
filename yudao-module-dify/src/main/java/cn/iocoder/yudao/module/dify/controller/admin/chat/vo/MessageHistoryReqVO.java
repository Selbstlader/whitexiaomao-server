package cn.iocoder.yudao.module.dify.controller.admin.chat.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 获取消息历史请求 VO
 *
 * @author 芋道源码
 */
@Schema(description = "管理后台 - 获取消息历史请求 VO")
@Data
public class MessageHistoryReqVO {

    @Schema(description = "用户标识", requiredMode = Schema.RequiredMode.REQUIRED, example = "user-123")
    @NotBlank(message = "用户标识不能为空")
    private String user;

    @Schema(description = "会话 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1c7e55fb-1ba2-4e10-81b5-30addcea2276")
    @NotBlank(message = "会话 ID 不能为空")
    private String conversationId;

    @Schema(description = "第一条消息的 ID，用于分页", example = "msg-123")
    private String firstId;

    @Schema(description = "每页数量", example = "20")
    private Integer limit = 20;

}
