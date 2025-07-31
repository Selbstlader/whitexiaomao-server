package cn.iocoder.yudao.module.cooking.controller.app.tip.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "用戶 APP - 烹飪小貼士 Response VO")
@Data
public class TipRespVO {

    @Schema(description = "小貼士編號", required = true, example = "1024")
    private Long id;

    @Schema(description = "菜品編號", required = true, example = "1024")
    private Long dishId;

    @Schema(description = "小貼士內容", required = true, example = "炒蛋時火候要適中")
    private String content;

    @Schema(description = "創建時間", required = true)
    private LocalDateTime createTime;

}