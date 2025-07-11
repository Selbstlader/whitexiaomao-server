package cn.iocoder.yudao.module.dify.controller.admin.chat.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Map;

/**
 * 会话响应 VO
 *
 * @author 芋道源码
 */
@Schema(description = "管理后台 - 会话响应 VO")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationRespVO {

    @Schema(description = "会话 ID", example = "1c7e55fb-1ba2-4e10-81b5-30addcea2276")
    private String id;

    @Schema(description = "会话名称", example = "新对话")
    private String name;

    @Schema(description = "输入参数", example = "{\"name\": \"张三\"}")
    private Map<String, Object> inputs;

    @Schema(description = "会话状态", example = "normal")
    private String status;

    @Schema(description = "会话介绍", example = "这是一个关于产品咨询的对话")
    private String introduction;

    @Schema(description = "创建时间", example = "1679667915")
    @JsonProperty("created_at")
    private Long createdAt;

    @Schema(description = "更新时间", example = "1679667915")
    @JsonProperty("updated_at")
    private Long updatedAt;

}
