package cn.iocoder.yudao.module.dify.controller.admin.chat.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 建议问题响应 VO
 *
 * @author 芋道源码
 */
@Schema(description = "管理后台 - 建议问题响应 VO")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SuggestedQuestionsRespVO {

    @Schema(description = "操作结果", example = "success")
    private String result;

    @Schema(description = "建议问题列表", example = "[\"你还想了解什么？\", \"有其他问题吗？\"]")
    private List<String> data;

}
