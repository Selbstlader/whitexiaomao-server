package cn.iocoder.yudao.module.douban.controller.admin.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;

/**
 * 豆瓣用户 Base VO，提供给添加、修改、详情使用
 */
@Data
public class UserBaseVO {

    @Schema(description = "用户名", required = true, example = "张三")
    @NotEmpty(message = "用户名不能为空")
    private String people;

    @Schema(description = "所在地区", example = "北京")
    private String location;

    @Schema(description = "个人简介", example = "电影爱好者")
    private String introduction;

    @Schema(description = "关注数", example = "100")
    private Integer friendCount;

    @Schema(description = "被关注数", example = "200")
    private Integer beAttentionCount;

}