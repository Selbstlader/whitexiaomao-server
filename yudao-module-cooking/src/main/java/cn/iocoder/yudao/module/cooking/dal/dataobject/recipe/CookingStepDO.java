package cn.iocoder.yudao.module.cooking.dal.dataobject.recipe;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;

/**
 * 烹饪步骤 DO
 *
 * @author 芋道源码
 */
@TableName("cooking_cooking_step")
@KeySequence("cooking_cooking_step_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CookingStepDO extends BaseDO {

    /**
     * 步骤编号
     */
    @TableId
    private Long id;
    /**
     * 菜谱ID
     */
    private Long recipeId;
    /**
     * 步骤顺序
     */
    private Integer stepOrder;
    /**
     * 步骤标题
     */
    private String title;
    /**
     * 步骤描述
     */
    private String description;
    /**
     * 步骤图片
     */
    private String image;
    /**
     * 烹饪时间（分钟）
     */
    private Integer cookingTime;
    /**
     * 烹饪温度（摄氏度）
     */
    private BigDecimal temperature;
    /**
     * 火候（如：大火、中火、小火）
     */
    private String heatLevel;
    /**
     * 技巧提示
     */
    private String tips;
    /**
     * 注意事项
     */
    private String notes;
    /**
     * 状态（0禁用 1启用）
     */
    private Integer status;

}
