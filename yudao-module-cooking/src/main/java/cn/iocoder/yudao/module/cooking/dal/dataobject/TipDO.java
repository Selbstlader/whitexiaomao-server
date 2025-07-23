package cn.iocoder.yudao.module.cooking.dal.dataobject;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 烹飪小貼士 DO
 *
 * @author 芋道源碼
 */
@TableName("cooking_tip")
@KeySequence("cooking_tip_seq") // 用於 Oracle、PostgreSQL、Kingbase、DB2、H2 資料庫的主鍵自增。如果是 MySQL 等資料庫，可不寫。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TipDO extends BaseDO {

    /**
     * 小貼士編號
     */
    @TableId
    private Long id;
    /**
     * 菜品編號
     */
    private Long dishId;
    /**
     * 小貼士內容
     */
    private String content;

} 