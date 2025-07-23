package cn.iocoder.yudao.module.cooking.controller.admin.step.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "管理後台 - 烹飪步驟批量創建 Request VO")
@Data
public class StepBatchCreateReqVO {

    @Schema(description = "菜品編號", required = true, example = "1024")
    @NotNull(message = "菜品編號不能為空")
    private Long dishId;

    @Schema(description = "步驟列表", required = true)
    @NotEmpty(message = "步驟列表不能為空")
    @Valid
    private List<StepInfoVO> steps;

    @Schema(description = "步驟基本信息")
    @Data
    public static class StepInfoVO {

        @Schema(description = "步驟順序", required = true, example = "1")
        @NotNull(message = "步驟順序不能為空")
        private Integer stepNumber;

        @Schema(description = "步驟描述", required = true, example = "將雞肉切成小塊")
        @NotEmpty(message = "步驟描述不能為空")
        private String description;
    }
} 