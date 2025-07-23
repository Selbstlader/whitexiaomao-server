package cn.iocoder.yudao.module.cooking.controller.admin.step.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import jakarta.validation.constraints.NotNull;

/**
 * 烹飪步驟更新 Request VO
 *
 * @author 芋道源碼
 */
@Schema(description = "管理後台 - 烹飪步驟更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StepUpdateReqVO extends StepBaseVO {

    @Schema(description = "步驟編號", required = true, example = "1024")
    @NotNull(message = "步驟編號不能為空")
    private Long id;

} 