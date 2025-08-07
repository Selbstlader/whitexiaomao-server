package cn.iocoder.yudao.module.douban.controller.admin.user.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 豆瓣用户分页 Request VO
 */
@Schema(description = "管理后台 - 豆瓣用户分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserPageReqVO extends PageParam {

    @Schema(description = "用户名", example = "张三")
    private String people;

    @Schema(description = "所在地区", example = "北京")
    private String location;

    @Schema(description = "最少好友数", example = "10")
    private Integer minFriendCount;

    @Schema(description = "最少关注数", example = "5")
    private Integer minAttentionCount;

}