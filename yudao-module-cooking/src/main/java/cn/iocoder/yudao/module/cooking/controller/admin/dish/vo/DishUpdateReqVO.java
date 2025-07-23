package cn.iocoder.yudao.module.cooking.controller.admin.dish.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;

/**
 * 菜品更新 Request VO
 *
 * @author 芋道源碼
 */
@Schema(description = "管理後台 - 菜品更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DishUpdateReqVO extends DishBaseVO {

    @Schema(description = "菜品編號", required = true, example = "1024")
    @NotNull(message = "菜品編號不能為空")
    private Long id;

    @Schema(description = "菜品圖片文件，如有上傳則會覆蓋原圖片")
    private MultipartFile imageFile;

} 