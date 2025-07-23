package cn.iocoder.yudao.module.cooking.controller.admin.tip.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "管理後台 - 烹飪小貼士 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TipRespVO extends TipBaseVO {

    @Schema(description = "小貼士編號", required = true, example = "1024")
    private Long id;

    @Schema(description = "創建時間", required = true)
    private LocalDateTime createTime;

} 