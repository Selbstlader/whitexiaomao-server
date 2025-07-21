package cn.iocoder.yudao.module.cooking.dal.dataobject.ingredient;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;

/**
 * 食材 DO
 *
 * @author 芋道源码
 */
@TableName("cooking_ingredient")
@KeySequence("cooking_ingredient_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDO extends BaseDO {

    /**
     * 食材编号
     */
    @TableId
    private Long id;
    /**
     * 食材名称
     */
    private String name;
    /**
     * 食材描述
     */
    private String description;
    /**
     * 食材图片
     */
    private String image;
    /**
     * 食材分类ID
     */
    private Long categoryId;
    /**
     * 单位（如：克、毫升、个等）
     */
    private String unit;
    /**
     * 每100g热量（卡路里）
     */
    private BigDecimal caloriesPer100g;
    /**
     * 每100g蛋白质（克）
     */
    private BigDecimal proteinPer100g;
    /**
     * 每100g脂肪（克）
     */
    private BigDecimal fatPer100g;
    /**
     * 每100g碳水化合物（克）
     */
    private BigDecimal carbohydratesPer100g;
    /**
     * 每100g纤维（克）
     */
    private BigDecimal fiberPer100g;
    /**
     * 每100g糖分（克）
     */
    private BigDecimal sugarPer100g;
    /**
     * 每100g钠（毫克）
     */
    private BigDecimal sodiumPer100g;
    /**
     * 季节性（JSON数组，如：["春","夏"]）
     */
    private String seasons;
    /**
     * 存储建议
     */
    private String storageAdvice;
    /**
     * 保质期（天）
     */
    private Integer shelfLife;
    /**
     * 状态（0禁用 1启用）
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;

}
