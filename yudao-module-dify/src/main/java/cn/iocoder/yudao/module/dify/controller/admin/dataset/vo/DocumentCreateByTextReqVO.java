package cn.iocoder.yudao.module.dify.controller.admin.dataset.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import java.util.Map;

/**
 * 通过文本创建文档请求 VO
 *
 * @author 芋道源码
 */
@Schema(description = "管理后台 - 通过文本创建文档请求 VO")
@Data
public class DocumentCreateByTextReqVO {

    @Schema(description = "文档名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "产品介绍")
    @NotBlank(message = "文档名称不能为空")
    private String name;

    @Schema(description = "文档文本内容", requiredMode = Schema.RequiredMode.REQUIRED, example = "这是一个产品介绍文档...")
    @NotBlank(message = "文档内容不能为空")
    private String text;

    @Schema(description = "索引技术", example = "high_quality")
    @JsonProperty("indexing_technique")
    private String indexingTechnique = "high_quality";

    @Schema(description = "处理规则")
    @JsonProperty("process_rule")
    private ProcessRule processRule;

    @Data
    @Schema(description = "处理规则")
    public static class ProcessRule {
        @Schema(description = "处理模式", example = "automatic")
        private String mode = "automatic";

        @Schema(description = "规则配置")
        private Map<String, Object> rules;
    }

}
