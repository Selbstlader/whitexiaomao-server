package cn.iocoder.yudao.module.dify.controller.admin.chat.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 消息反馈响应 VO
 *
 * @author 芋道源码
 */
@Schema(description = "管理后台 - 消息反馈响应 VO")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageFeedbackRespVO {

    @Schema(description = "操作结果", example = "success")
    private String result;

}
