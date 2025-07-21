package cn.iocoder.yudao.module.cooking.dal.dataobject.recipe;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;

/**
 * 菜谱食材关联 DO
 *
 * @author 芋道源码
 */
@TableName("cooking_recipe_ingredient")
@KeySequence("cooking_recipe_ingredient_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredientDO extends BaseDO {

    /**
     * 关联编号
     */
    @TableId
    private Long id;
    /**
     * 菜谱ID
     */
    private Long recipeId;
    /**
     * 食材ID
     */
    private Long ingredientId;
    /**
     * 用量
     */
    private BigDecimal amount;
    /**
     * 单位
     */
    private String unit;
    /**
     * 是否必需（0可选 1必需）
     */
    private Integer required;
    /**
     * 备注（如：切丝、切块等处理方式）
     */
    private String remark;

}
