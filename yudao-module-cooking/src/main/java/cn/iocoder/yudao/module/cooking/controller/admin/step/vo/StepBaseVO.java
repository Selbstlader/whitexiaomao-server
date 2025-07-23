package cn.iocoder.yudao.module.cooking.controller.admin.step.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * 烹飪步驟 Base VO，提供給添加、修改、詳情使用
 */
@Data
public class StepBaseVO {

    @Schema(description = "菜品編號", required = true, example = "1024")
    @NotNull(message = "菜品編號不能為空")
    private Long dishId;

    @Schema(description = "步驟順序", required = true, example = "1")
    @NotNull(message = "步驟順序不能為空")
    private Integer stepNumber;

    @Schema(description = "步驟描述", required = true, example = "將雞肉切成小塊")
    @NotEmpty(message = "步驟描述不能為空")
    private String description;

    @Schema(description = "步驟圖片名稱", example = "step_1.jpg")
    private String imageName;

    @Schema(description = "步驟圖片文件")
    private MultipartFile imageFile;

} 