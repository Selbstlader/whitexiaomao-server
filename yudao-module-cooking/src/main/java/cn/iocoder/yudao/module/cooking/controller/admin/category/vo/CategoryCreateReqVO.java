package cn.iocoder.yudao.module.cooking.controller.admin.category.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 菜品分類創建 Request VO
 *
 * @author 芋道源碼
 */
@Schema(description = "管理後台 - 菜品分類創建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CategoryCreateReqVO extends CategoryBaseVO {

} 