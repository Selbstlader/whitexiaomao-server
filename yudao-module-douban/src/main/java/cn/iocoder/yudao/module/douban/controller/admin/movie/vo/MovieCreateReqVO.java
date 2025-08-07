package cn.iocoder.yudao.module.douban.controller.admin.movie.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 豆瓣电影创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MovieCreateReqVO extends MovieBaseVO {

}