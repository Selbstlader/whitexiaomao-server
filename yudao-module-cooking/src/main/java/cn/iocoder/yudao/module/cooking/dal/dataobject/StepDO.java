package cn.iocoder.yudao.module.cooking.dal.dataobject;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 烹飪步驟 DO
 *
 * @author 芋道源碼
 */
@TableName("cooking_step")
@KeySequence("cooking_step_seq") // 用於 Oracle、PostgreSQL、Kingbase、DB2、H2 資料庫的主鍵自增。如果是 MySQL 等資料庫，可不寫。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StepDO extends BaseDO {

    /**
     * 步驟編號
     */
    @TableId
    private Long id;
    /**
     * 菜品編號
     */
    private Long dishId;
    /**
     * 步驟順序
     */
    private Integer stepNumber;
    /**
     * 步驟描述
     */
    private String description;
    /**
     * 步驟圖片名稱
     */
    @TableField("image_url")
    private String imageName;

}