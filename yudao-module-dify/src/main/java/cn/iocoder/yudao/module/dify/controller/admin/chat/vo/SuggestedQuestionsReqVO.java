package cn.iocoder.yudao.module.dify.controller.admin.chat.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 获取建议问题请求 VO
 *
 * @author 芋道源码
 */
@Schema(description = "管理后台 - 获取建议问题请求 VO")
@Data
public class SuggestedQuestionsReqVO {

    @Schema(description = "消息 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "msg-123")
    @NotBlank(message = "消息 ID 不能为空")
    private String messageId;

    @Schema(description = "用户标识", requiredMode = Schema.RequiredMode.REQUIRED, example = "user-123")
    @NotBlank(message = "用户标识不能为空")
    private String user;

}
