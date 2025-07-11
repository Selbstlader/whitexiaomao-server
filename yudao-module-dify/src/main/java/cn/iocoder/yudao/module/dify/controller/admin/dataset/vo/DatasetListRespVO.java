package cn.iocoder.yudao.module.dify.controller.admin.dataset.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 知识库列表响应 VO
 *
 * @author 芋道源码
 */
@Schema(description = "管理后台 - 知识库列表响应 VO")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DatasetListRespVO {

    @Schema(description = "知识库列表")
    private List<DatasetRespVO> data;

    @Schema(description = "是否有更多数据", example = "true")
    @JsonProperty("has_more")
    private Boolean hasMore;

    @Schema(description = "每页数量", example = "20")
    private Integer limit;

    @Schema(description = "总数量", example = "50")
    private Long total;

    @Schema(description = "当前页码", example = "1")
    private Integer page;

}
