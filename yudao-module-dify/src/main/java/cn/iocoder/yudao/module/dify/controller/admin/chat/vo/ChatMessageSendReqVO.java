package cn.iocoder.yudao.module.dify.controller.admin.chat.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * 发送对话消息请求 VO
 *
 * @author 芋道源码
 */
@Schema(description = "管理后台 - 发送对话消息请求 VO")
@Data
public class ChatMessageSendReqVO {

    @Schema(description = "用户输入/提问内容", requiredMode = Schema.RequiredMode.REQUIRED, example = "你好，请介绍一下你自己")
    @NotBlank(message = "消息内容不能为空")
    private String query;

    @Schema(description = "原始文本内容（Dify 特定字段）", example = "你好，请介绍一下你自己")
    @JsonProperty("original_text")
    private String originalText;

    @Schema(description = "允许传入 App 定义的各变量值，默认为空对象", example = "{\"name\": \"张三\"}")
    private Map<String, Object> inputs;

    @Schema(description = "响应模式：streaming 流式模式（推荐），blocking 阻塞模式", example = "blocking")
    @JsonProperty("response_mode")
    private String responseMode = "blocking";

    @Schema(description = "用户标识，用于定义终端用户的身份（系统自动设置）", example = "client-1")
    private String user;

    @Schema(description = "会话 ID，需要基于之前的聊天记录继续对话时传入", example = "1c7e55fb-1ba2-4e10-81b5-30addcea2276")
    @JsonProperty("conversation_id")
    private String conversationId;

    @Schema(description = "上传的文件列表")
    private List<FileInfo> files;

    @Schema(description = "自动生成标题，默认 true", example = "true")
    @JsonProperty("auto_generate_name")
    private Boolean autoGenerateName = true;

    /**
     * 文件信息
     */
    @Data
    @Schema(description = "文件信息")
    public static class FileInfo {

        @Schema(description = "文件类型，目前仅支持 image", example = "image")
        private String type;

        @Schema(description = "传递方式：remote_url 图片地址，local_file 上传文件", example = "remote_url")
        @JsonProperty("transfer_method")
        private String transferMethod;

        @Schema(description = "图片地址（仅当传递方式为 remote_url 时）", example = "https://example.com/image.jpg")
        private String url;

        @Schema(description = "上传文件 ID（仅当传递方式为 local_file 时）", example = "file-123")
        @JsonProperty("upload_file_id")
        private String uploadFileId;
    }

    // Getter and Setter for originalText
    public String getOriginalText() {
        return originalText;
    }

    public void setOriginalText(String originalText) {
        this.originalText = originalText;
    }

}
