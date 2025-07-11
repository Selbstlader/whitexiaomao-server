package cn.iocoder.yudao.module.dify.controller.admin.dataset.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Map;

/**
 * 文档响应 VO
 *
 * @author 芋道源码
 */
@Schema(description = "管理后台 - 文档响应 VO")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentRespVO {

    @Schema(description = "文档 ID", example = "doc-123")
    private String id;

    @Schema(description = "文档位置", example = "1")
    private Integer position;

    @Schema(description = "数据源类型", example = "upload_file")
    @JsonProperty("data_source_type")
    private String dataSourceType;

    @Schema(description = "数据源信息")
    @JsonProperty("data_source_info")
    private Map<String, Object> dataSourceInfo;

    @Schema(description = "数据集处理规则 ID")
    @JsonProperty("dataset_process_rule_id")
    private String datasetProcessRuleId;

    @Schema(description = "文档名称", example = "产品介绍.txt")
    private String name;

    @Schema(description = "创建来源", example = "api")
    @JsonProperty("created_from")
    private String createdFrom;

    @Schema(description = "创建者", example = "admin")
    @JsonProperty("created_by")
    private String createdBy;

    @Schema(description = "创建时间", example = "1695690280")
    @JsonProperty("created_at")
    private Long createdAt;

    @Schema(description = "令牌数量", example = "100")
    private Integer tokens;

    @Schema(description = "索引状态", example = "completed")
    @JsonProperty("indexing_status")
    private String indexingStatus;

    @Schema(description = "错误信息")
    private String error;

    @Schema(description = "是否启用", example = "true")
    private Boolean enabled;

    @Schema(description = "禁用时间")
    @JsonProperty("disabled_at")
    private Long disabledAt;

    @Schema(description = "禁用者")
    @JsonProperty("disabled_by")
    private String disabledBy;

    @Schema(description = "是否归档", example = "false")
    private Boolean archived;

    @Schema(description = "显示状态", example = "completed")
    @JsonProperty("display_status")
    private String displayStatus;

    @Schema(description = "词汇数量", example = "500")
    @JsonProperty("word_count")
    private Integer wordCount;

    @Schema(description = "命中次数", example = "10")
    @JsonProperty("hit_count")
    private Integer hitCount;

    @Schema(description = "文档形式", example = "text_model")
    @JsonProperty("doc_form")
    private String docForm;

}
