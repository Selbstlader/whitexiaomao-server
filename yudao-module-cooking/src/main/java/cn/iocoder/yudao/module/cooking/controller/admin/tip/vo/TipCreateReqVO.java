package cn.iocoder.yudao.module.cooking.controller.admin.tip.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理後台 - 烹飪小貼士創建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TipCreateReqVO extends TipBaseVO {

} 