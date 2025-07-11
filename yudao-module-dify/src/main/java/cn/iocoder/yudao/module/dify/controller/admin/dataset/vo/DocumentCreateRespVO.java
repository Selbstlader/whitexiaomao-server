package cn.iocoder.yudao.module.dify.controller.admin.dataset.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 文档创建响应 VO
 *
 * @author 芋道源码
 */
@Schema(description = "管理后台 - 文档创建响应 VO")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentCreateRespVO {

    @Schema(description = "文档信息")
    private DocumentRespVO document;

    @Schema(description = "批次号", example = "20230921150427533684")
    private String batch;

}
