package cn.iocoder.yudao.module.cooking.dal.dataobject;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 配料 DO
 *
 * @author 芋道源碼
 */
@TableName("cooking_ingredient")
@KeySequence("ingredient_seq") // 用於 Oracle、PostgreSQL、Kingbase、DB2、H2 資料庫的主鍵自增。如果是 MySQL 等資料庫，可不寫。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class IngredientDO extends BaseDO {

    /**
     * 配料編號
     */
    @TableId
    private Long id;
    /**
     * 菜品編號
     */
    private Long dishId;
    /**
     * 配料名稱
     */
    private String name;
    /**
     * 配料份量
     */
    private String amount;
    /**
     * 配料單位
     */
    private String unit;
    /**
     * 是否為可選配料
     * 0 - 必選
     * 1 - 可選
     */
    private Boolean isOptional;

}