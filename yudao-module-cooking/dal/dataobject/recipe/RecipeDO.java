package cn.iocoder.yudao.module.cooking.dal.dataobject.recipe;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;

/**
 * 菜谱 DO
 */
@TableName("cooking_recipe")
@KeySequence("cooking_recipe_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDO extends BaseDO {
    /** 菜谱编号 */
    @TableId
    private Long id;
    /** 菜谱名称 */
    private String name;
    /** 菜谱描述 */
    private String description;
    /** 菜谱封面图片 */
    private String coverImage;
    /** 菜系分类ID */
    private Long categoryId;
    /** 菜谱来源URL */
    private String sourceUrl;
    /** 菜谱来源 */
    private String source;
    /** 难度等级 */
    private Integer difficulty;
    /** 准备时间（分钟） */
    private Integer prepTime;
    /** 烹饪时间（分钟） */
    private Integer cookTime;
    /** 总时间（分钟） */
    private Integer totalTime;
    /** 份数 */
    private Integer servings;
    /** 热量（卡路里） */
    private BigDecimal calories;
    /** 蛋白质（克） */
    private BigDecimal protein;
    /** 脂肪（克） */
    private BigDecimal fat;
    /** 碳水化合物（克） */
    private BigDecimal carbohydrates;
    /** 纤维（克） */
    private BigDecimal fiber;
    /** 糖分（克） */
    private BigDecimal sugar;
    /** 钠（毫克） */
    private BigDecimal sodium;
    /** 状态 */
    private Integer status;
    /** 创建者用户ID */
    private Long creatorId;
    /** 浏览次数 */
    private Integer viewCount;
    /** 收藏次数 */
    private Integer favoriteCount;
    /** 评分（1-5星） */
    private BigDecimal rating;
    /** 评分次数 */
    private Integer ratingCount;
    /** 标签（JSON数组） */
    private String tags;
    /** 备注 */
    private String remark;
}
