package cn.iocoder.yudao.module.dify.controller.admin.chat.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 会话列表响应 VO
 *
 * @author 芋道源码
 */
@Schema(description = "管理后台 - 会话列表响应 VO")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationListRespVO {

    @Schema(description = "每页数量", example = "20")
    private Integer limit;

    @Schema(description = "是否有更多数据", example = "false")
    @JsonProperty("has_more")
    private Boolean hasMore;

    @Schema(description = "会话列表")
    private List<ConversationRespVO> data;

}
