package cn.iocoder.yudao.module.douban.controller.admin.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 豆瓣用户创建 Request VO
 */
@Schema(description = "管理后台 - 豆瓣用户创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserCreateReqVO extends UserBaseVO {

}