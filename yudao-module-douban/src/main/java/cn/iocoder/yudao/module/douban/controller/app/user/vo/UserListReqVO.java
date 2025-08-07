package cn.iocoder.yudao.module.douban.controller.app.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "移动端 - 豆瓣用户列表 Request VO")
@Data
public class UserListReqVO {

    @Schema(description = "用户名，模糊匹配", example = "张")
    private String people;

    @Schema(description = "所在地区", example = "北京")
    private String location;

}