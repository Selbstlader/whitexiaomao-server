package cn.iocoder.yudao.module.douban.controller.admin.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import jakarta.validation.constraints.NotNull;

/**
 * 豆瓣用户更新 Request VO
 */
@Schema(description = "管理后台 - 豆瓣用户更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserUpdateReqVO extends UserBaseVO {

    @Schema(description = "用户编号", required = true, example = "1024")
    @NotNull(message = "用户编号不能为空")
    private Long id;

}