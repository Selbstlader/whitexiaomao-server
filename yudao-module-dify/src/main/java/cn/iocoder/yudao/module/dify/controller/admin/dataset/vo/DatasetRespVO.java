package cn.iocoder.yudao.module.dify.controller.admin.dataset.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 知识库响应 VO
 *
 * @author 芋道源码
 */
@Schema(description = "管理后台 - 知识库响应 VO")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DatasetRespVO {

    @Schema(description = "知识库 ID", example = "550e8400-e29b-41d4-a716-446655440000")
    private String id;

    @Schema(description = "知识库名称", example = "产品知识库")
    private String name;

    @Schema(description = "知识库描述", example = "包含产品相关的所有文档和资料")
    private String description;

    @Schema(description = "提供商", example = "vendor")
    private String provider;

    @Schema(description = "权限设置", example = "only_me")
    private String permission;

    @Schema(description = "数据源类型", example = "upload_file")
    @JsonProperty("data_source_type")
    private String dataSourceType;

    @Schema(description = "索引技术", example = "high_quality")
    @JsonProperty("indexing_technique")
    private String indexingTechnique;

    @Schema(description = "应用数量", example = "2")
    @JsonProperty("app_count")
    private Integer appCount;

    @Schema(description = "文档数量", example = "10")
    @JsonProperty("document_count")
    private Integer documentCount;

    @Schema(description = "词汇数量", example = "1200")
    @JsonProperty("word_count")
    private Integer wordCount;

    @Schema(description = "创建者", example = "admin")
    @JsonProperty("created_by")
    private String createdBy;

    @Schema(description = "创建时间", example = "1695636173")
    @JsonProperty("created_at")
    private Long createdAt;

    @Schema(description = "更新者", example = "admin")
    @JsonProperty("updated_by")
    private String updatedBy;

    @Schema(description = "更新时间", example = "1695636173")
    @JsonProperty("updated_at")
    private Long updatedAt;

    @Schema(description = "嵌入模型", example = "text-embedding-ada-002")
    @JsonProperty("embedding_model")
    private String embeddingModel;

    @Schema(description = "嵌入模型提供商", example = "openai")
    @JsonProperty("embedding_model_provider")
    private String embeddingModelProvider;

    @Schema(description = "嵌入是否可用", example = "true")
    @JsonProperty("embedding_available")
    private Boolean embeddingAvailable;

    @Schema(description = "检索模型配置")
    @JsonProperty("retrieval_model_dict")
    private Object retrievalModelDict;

}
