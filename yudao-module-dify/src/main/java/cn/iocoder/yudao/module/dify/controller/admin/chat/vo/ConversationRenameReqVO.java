package cn.iocoder.yudao.module.dify.controller.admin.chat.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 重命名会话请求 VO
 *
 * @author 芋道源码
 */
@Schema(description = "管理后台 - 重命名会话请求 VO")
@Data
public class ConversationRenameReqVO {

    @Schema(description = "会话 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "conv-123")
    @NotBlank(message = "会话 ID 不能为空")
    private String conversationId;

    @Schema(description = "新的会话名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "我的对话")
    @NotBlank(message = "会话名称不能为空")
    private String name;

    @Schema(description = "用户标识（系统自动设置）", example = "client-1")
    private String user;

    @Schema(description = "是否自动生成", example = "false")
    private Boolean autoGenerate = false;

}
