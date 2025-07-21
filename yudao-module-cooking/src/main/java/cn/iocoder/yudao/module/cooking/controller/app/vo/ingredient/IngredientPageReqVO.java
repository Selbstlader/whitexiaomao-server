package cn.iocoder.yudao.module.cooking.controller.app.vo.ingredient;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "用户 APP - 食材分页查询 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class IngredientPageReqVO extends PageParam {

    @Schema(description = "食材名称", example = "土豆")
    private String name;

    @Schema(description = "食材分类ID", example = "1")
    private Long categoryId;
    
    @Schema(description = "排序字段", example = "calories_per_100g")
    private String sortField;
    
    @Schema(description = "排序方式(asc/desc)", example = "asc")
    private String sortOrder;
} 