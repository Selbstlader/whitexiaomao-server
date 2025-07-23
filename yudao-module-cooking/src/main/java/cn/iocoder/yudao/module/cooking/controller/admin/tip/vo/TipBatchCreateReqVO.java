package cn.iocoder.yudao.module.cooking.controller.admin.tip.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "管理後台 - 烹飪小貼士批量創建 Request VO")
@Data
public class TipBatchCreateReqVO {

    @Schema(description = "菜品編號", required = true, example = "1024")
    @NotNull(message = "菜品編號不能為空")
    private Long dishId;

    @Schema(description = "小貼士列表", required = true)
    @NotEmpty(message = "小貼士列表不能為空")
    @Valid
    private List<TipItemVO> tips;

    @Schema(description = "小貼士基本信息")
    @Data
    public static class TipItemVO {

        @Schema(description = "小貼士內容", required = true, example = "炒菜時加點鹽可以提鮮")
        @NotEmpty(message = "小貼士內容不能為空")
        private String content;
    }
} 