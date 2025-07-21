package cn.iocoder.yudao.module.cooking.controller.app.vo.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Schema(description = "用户 APP - 分类信息 Response VO")
@Data
@ToString(callSuper = true)
public class CategoryRespVO {

    @Schema(description = "分类编号", required = true, example = "1")
    private Long id;

    @Schema(description = "分类名称", required = true, example = "川菜")
    private String name;

    @Schema(description = "分类描述", example = "麻辣鲜香的川菜系列")
    private String description;

    @Schema(description = "分类图标", example = "icon-chuancai")
    private String icon;

    @Schema(description = "分类图片", example = "https://www.example.com/images/chuancai.jpg")
    private String image;

    @Schema(description = "父分类ID", example = "0")
    private Long parentId;

    @Schema(description = "分类类型（1菜谱分类 2食材分类）", required = true, example = "1")
    private Integer type;

    @Schema(description = "排序", required = true, example = "1")
    private Integer sort;

    @Schema(description = "状态（0禁用 1启用）", required = true, example = "1")
    private Integer status;
} 