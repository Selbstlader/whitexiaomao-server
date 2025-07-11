package cn.iocoder.yudao.module.dify.controller.admin.chat.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 对话消息响应 VO
 *
 * @author 芋道源码
 */
@Schema(description = "管理后台 - 对话消息响应 VO")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatMessageRespVO {

    @Schema(description = "事件类型，固定为 message", example = "message")
    private String event;

    @Schema(description = "任务 ID，用于请求跟踪", example = "900bbd43-dc0b-4383-a372-aa6e6c414227")
    @JsonProperty("task_id")
    private String taskId;

    @Schema(description = "消息唯一 ID", example = "9da23599-e713-473b-982c-4328d4f5c78a")
    private String id;

    @Schema(description = "消息唯一 ID", example = "9da23599-e713-473b-982c-4328d4f5c78a")
    @JsonProperty("message_id")
    private String messageId;

    @Schema(description = "会话 ID", example = "1c7e55fb-1ba2-4e10-81b5-30addcea2276")
    @JsonProperty("conversation_id")
    private String conversationId;

    @Schema(description = "App 模式，固定为 chat", example = "chat")
    private String mode;

    @Schema(description = "完整回复内容", example = "你好！我是 AI 助手，很高兴为您服务。")
    private String answer;

    @Schema(description = "元数据")
    private MetadataInfo metadata;

    @Schema(description = "消息创建时间戳", example = "1705395332")
    @JsonProperty("created_at")
    private Long createdAt;

    /**
     * 元数据信息
     */
    @Data
    @Schema(description = "元数据信息")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MetadataInfo {

        @Schema(description = "模型用量信息")
        private UsageInfo usage;

        @Schema(description = "引用和归属分段列表")
        @JsonProperty("retriever_resources")
        private List<RetrieverResource> retrieverResources;
    }

    /**
     * 模型用量信息
     */
    @Data
    @Schema(description = "模型用量信息")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UsageInfo {

        @Schema(description = "提示词 tokens", example = "100")
        @JsonProperty("prompt_tokens")
        private Integer promptTokens;

        @Schema(description = "完成 tokens", example = "50")
        @JsonProperty("completion_tokens")
        private Integer completionTokens;

        @Schema(description = "总 tokens", example = "150")
        @JsonProperty("total_tokens")
        private Integer totalTokens;

        @Schema(description = "提示词价格", example = "0.001")
        @JsonProperty("prompt_price")
        private String promptPrice;

        @Schema(description = "完成价格", example = "0.002")
        @JsonProperty("completion_price")
        private String completionPrice;

        @Schema(description = "总价格", example = "0.003")
        @JsonProperty("total_price")
        private String totalPrice;

        @Schema(description = "货币单位", example = "USD")
        private String currency;

        @Schema(description = "延迟时间", example = "1.5")
        private Double latency;
    }

    /**
     * 检索资源
     */
    @Data
    @Schema(description = "检索资源")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RetrieverResource {

        @Schema(description = "位置", example = "1")
        private Integer position;

        @Schema(description = "数据集 ID", example = "dataset-123")
        @JsonProperty("dataset_id")
        private String datasetId;

        @Schema(description = "数据集名称", example = "产品知识库")
        @JsonProperty("dataset_name")
        private String datasetName;

        @Schema(description = "文档 ID", example = "doc-123")
        @JsonProperty("document_id")
        private String documentId;

        @Schema(description = "文档名称", example = "产品介绍.pdf")
        @JsonProperty("document_name")
        private String documentName;

        @Schema(description = "数据源类型", example = "upload_file")
        @JsonProperty("data_source_type")
        private String dataSourceType;

        @Schema(description = "分段 ID", example = "segment-456")
        @JsonProperty("segment_id")
        private String segmentId;

        @Schema(description = "相似度分数", example = "0.95")
        private Double score;

        @Schema(description = "分段内容", example = "这是一个产品介绍的片段...")
        private String content;
    }

}
