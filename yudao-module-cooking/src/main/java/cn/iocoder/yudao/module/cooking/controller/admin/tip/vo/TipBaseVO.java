package cn.iocoder.yudao.module.cooking.controller.admin.tip.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * 烹飪小貼士 Base VO，提供給添加、修改、詳情使用
 */
@Data
public class TipBaseVO {

    @Schema(description = "菜品編號", required = true, example = "1024")
    @NotNull(message = "菜品編號不能為空")
    private Long dishId;

    @Schema(description = "小貼士內容", required = true, example = "炒菜時加點鹽可以提鮮")
    @NotEmpty(message = "小貼士內容不能為空")
    private String content;

} 