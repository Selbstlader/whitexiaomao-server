package cn.iocoder.yudao.module.cooking.dal.dataobject;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import cn.iocoder.yudao.module.cooking.enums.DishDifficultyEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 菜品 DO
 *
 * @author 芋道源碼
 */
@TableName("cooking_dish")
@KeySequence("cooking_dish_seq") // 用於 Oracle、PostgreSQL、Kingbase、DB2、H2 資料庫的主鍵自增。如果是 MySQL 等資料庫，可不寫。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DishDO extends BaseDO {

    /**
     * 菜品編號
     */
    @TableId
    private Long id;
    /**
     * 分類編號
     */
    private Long categoryId;
    /**
     * 菜品名稱
     */
    private String name;
    /**
     * 菜品描述
     */
    private String description;
    /**
     * 菜品圖片名稱
     */
    @TableField("image_name")
    private String imageName;
    /**
     * 難度等級
     *
     * 枚舉 {@link DishDifficultyEnum}
     */
    private Integer difficulty;
    /**
     * 烹飪時間（分鐘）
     */
    private Integer cookingTime;

}