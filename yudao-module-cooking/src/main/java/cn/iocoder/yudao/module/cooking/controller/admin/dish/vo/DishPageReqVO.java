package cn.iocoder.yudao.module.cooking.controller.admin.dish.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理後台 - 菜品分頁 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DishPageReqVO extends PageParam {

    @Schema(description = "菜品名稱，模糊匹配", example = "紅燒")
    private String name;

    @Schema(description = "分類編號", example = "1")
    private Long categoryId;

    @Schema(description = "難度等級", example = "3")
    private Integer difficulty;

} 