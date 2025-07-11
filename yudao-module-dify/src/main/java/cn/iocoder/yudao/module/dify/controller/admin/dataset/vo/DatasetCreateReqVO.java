package cn.iocoder.yudao.module.dify.controller.admin.dataset.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 创建知识库请求 VO
 *
 * @author 芋道源码
 */
@Schema(description = "管理后台 - 创建知识库请求 VO")
@Data
public class DatasetCreateReqVO {

    @Schema(description = "知识库名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "产品知识库")
    @NotBlank(message = "知识库名称不能为空")
    private String name;

    @Schema(description = "知识库描述", example = "包含产品相关的所有文档和资料")
    private String description;

    @Schema(description = "权限设置", example = "only_me")
    private String permission = "only_me";

}
