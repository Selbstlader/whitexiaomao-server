package cn.iocoder.yudao.module.cooking.controller.admin.category.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 菜品分類 Base VO，提供給添加、修改、詳情的子 VO 使用
 * 如果子 VO 存在差異的字段，請不要添加到這裡，影響 Swagger 文檔生成
 *
 * @author 芋道源碼
 */
@Data
public class CategoryBaseVO {

    @Schema(description = "分類名稱", requiredMode = Schema.RequiredMode.REQUIRED, example = "川菜")
    @NotBlank(message = "分類名稱不能為空")
    @Size(max = 100, message = "分類名稱長度不能超過 100 個字符")
    private String name;

} 