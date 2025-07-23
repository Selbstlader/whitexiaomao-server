package cn.iocoder.yudao.module.cooking.controller.admin.category.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 菜品分類分頁 Request VO
 *
 * @author 芋道源碼
 */
@Schema(description = "管理後台 - 菜品分類分頁 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CategoryPageReqVO extends PageParam {

    @Schema(description = "分類名稱", example = "川菜")
    private String name;

} 