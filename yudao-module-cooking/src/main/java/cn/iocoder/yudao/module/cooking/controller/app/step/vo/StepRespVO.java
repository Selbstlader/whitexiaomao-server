package cn.iocoder.yudao.module.cooking.controller.app.step.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "移动端 - 烹飪步驟 Response VO")
@Data
public class StepRespVO {

    @Schema(description = "步驟編號", required = true, example = "1024")
    private Long id;

    @Schema(description = "菜品編號", required = true, example = "1")
    private Long dishId;

    @Schema(description = "步驟順序", required = true, example = "1")
    private Integer stepOrder;

    @Schema(description = "步驟描述", required = true, example = "將雞肉切成小塊")
    private String description;

    @Schema(description = "步驟圖片名稱", example = "step1.jpg")
    private String imageName;

    @Schema(description = "創建時間", required = true)
    private LocalDateTime createTime;
    
    @Schema(description = "步驟圖片URL")
    private String imageUrl;
    
    @Schema(description = "菜品名稱")
    private String dishName;

}