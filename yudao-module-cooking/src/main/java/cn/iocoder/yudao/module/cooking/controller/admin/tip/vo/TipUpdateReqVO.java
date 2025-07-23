package cn.iocoder.yudao.module.cooking.controller.admin.tip.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import jakarta.validation.constraints.NotNull;

@Schema(description = "管理後台 - 烹飪小貼士更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TipUpdateReqVO extends TipBaseVO {

    @Schema(description = "小貼士編號", required = true, example = "1024")
    @NotNull(message = "小貼士編號不能為空")
    private Long id;

} 