package cn.iocoder.yudao.module.cooking.controller.admin.dish.vo;

import cn.iocoder.yudao.module.cooking.controller.admin.ingredient.vo.IngredientRespVO;
import cn.iocoder.yudao.module.cooking.controller.admin.step.vo.StepRespVO;
import cn.iocoder.yudao.module.cooking.controller.admin.tip.vo.TipRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Schema(description = "管理後台 - 菜品詳情 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DishDetailRespVO extends DishRespVO {

    @Schema(description = "配料列表", required = true)
    private List<IngredientRespVO> ingredients;

    @Schema(description = "烹飪步驟列表", required = true)
    private List<StepRespVO> steps;

    @Schema(description = "烹飪小貼士列表", required = true)
    private List<TipRespVO> tips;

    @Schema(description = "平均星級評分", required = true)
    private Double averageStarRating;

} 