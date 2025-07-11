package cn.iocoder.yudao.module.dify.controller.admin.chat.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 消息历史响应 VO
 *
 * @author 芋道源码
 */
@Schema(description = "管理后台 - 消息历史响应 VO")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageHistoryRespVO {

    @Schema(description = "每页数量", example = "20")
    private Integer limit;

    @Schema(description = "是否有更多数据", example = "false")
    @JsonProperty("has_more")
    private Boolean hasMore;

    @Schema(description = "消息列表")
    private List<MessageInfo> data;

    /**
     * 消息信息
     */
    @Data
    @Schema(description = "消息信息")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MessageInfo {
        
        @Schema(description = "消息 ID", example = "msg-123")
        private String id;

        @Schema(description = "会话 ID", example = "conv-123")
        @JsonProperty("conversation_id")
        private String conversationId;

        @Schema(description = "输入参数", example = "{\"name\": \"张三\"}")
        private Map<String, Object> inputs;

        @Schema(description = "用户问题", example = "你好")
        private String query;

        @Schema(description = "AI 回答", example = "你好！我是 AI 助手。")
        private String answer;

        @Schema(description = "消息文件列表")
        @JsonProperty("message_files")
        private List<MessageFile> messageFiles;

        @Schema(description = "反馈信息")
        private FeedbackInfo feedback;

        @Schema(description = "检索资源列表")
        @JsonProperty("retriever_resources")
        private List<RetrieverResource> retrieverResources;

        @Schema(description = "Agent 思考过程")
        @JsonProperty("agent_thoughts")
        private List<Object> agentThoughts;

        @Schema(description = "创建时间", example = "1705569239")
        @JsonProperty("created_at")
        private Long createdAt;
    }

    /**
     * 消息文件
     */
    @Data
    @Schema(description = "消息文件")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MessageFile {
        
        @Schema(description = "文件 ID", example = "file-123")
        private String id;

        @Schema(description = "文件类型", example = "image")
        private String type;

        @Schema(description = "文件 URL", example = "https://example.com/file.jpg")
        private String url;

        @Schema(description = "文件归属", example = "user")
        @JsonProperty("belongs_to")
        private String belongsTo;
    }

    /**
     * 反馈信息
     */
    @Data
    @Schema(description = "反馈信息")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FeedbackInfo {
        
        @Schema(description = "评分", example = "like")
        private String rating;
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

        @Schema(description = "分段 ID", example = "segment-456")
        @JsonProperty("segment_id")
        private String segmentId;

        @Schema(description = "相似度分数", example = "0.98457545")
        private Double score;

        @Schema(description = "分段内容", example = "这是一个产品介绍的片段...")
        private String content;
    }

}
