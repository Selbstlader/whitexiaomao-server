package cn.iocoder.yudao.module.dify.controller.admin.chat.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 停止对话生成请求 VO
 *
 * @author 芋道源码
 */
@Schema(description = "管理后台 - 停止对话生成请求 VO")
@Data
public class ChatStopReqVO {

    @Schema(description = "任务 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "900bbd43-dc0b-4383-a372-aa6e6c414227")
    @NotBlank(message = "任务 ID 不能为空")
    private String taskId;

    @Schema(description = "用户标识（系统自动设置）", example = "client-1")
    private String user;

}
