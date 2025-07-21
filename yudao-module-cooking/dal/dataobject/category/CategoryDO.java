package cn.iocoder.yudao.module.cooking.dal.dataobject.category;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 分类 DO
 */
@TableName("cooking_category")
@KeySequence("cooking_category_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDO extends BaseDO {
    /** 分类编号 */
    @TableId
    private Long id;
    /** 分类名称 */
    private String name;
    /** 分类描述 */
    private String description;
    /** 分类图标 */
    private String icon;
    /** 分类图片 */
    private String image;
    /** 父分类ID */
    private Long parentId;
    /** 分类类型（1菜谱分类 2食材分类） */
    private Integer type;
    /** 排序 */
    private Integer sort;
    /** 状态（0禁用 1启用） */
    private Integer status;
} 