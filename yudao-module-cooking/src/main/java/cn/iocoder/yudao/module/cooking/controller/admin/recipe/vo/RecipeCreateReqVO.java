package cn.iocoder.yudao.module.cooking.controller.admin.recipe.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 菜谱创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RecipeCreateReqVO extends RecipeBaseVO {

}
