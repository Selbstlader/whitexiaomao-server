package cn.iocoder.yudao.module.douban.controller.app.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "移动端 - 豆瓣用户 Response VO")
@Data
public class UserRespVO {

    @Schema(description = "用户编号", required = true, example = "1024")
    private Long id;

    @Schema(description = "用户名", required = true, example = "张三")
    private String people;

    @Schema(description = "所在地区", example = "北京")
    private String location;

    @Schema(description = "个人简介", example = "电影爱好者")
    private String introduction;

    @Schema(description = "关注数", example = "100")
    private Integer friendCount;

    @Schema(description = "被关注数", example = "200")
    private Integer beAttentionCount;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

}