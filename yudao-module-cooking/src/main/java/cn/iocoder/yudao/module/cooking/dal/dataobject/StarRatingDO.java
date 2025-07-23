package cn.iocoder.yudao.module.cooking.dal.dataobject;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 菜品星級評分 DO
 *
 * @author 芋道源碼
 */
@TableName("cooking_star_rating")
@KeySequence("cooking_star_rating_seq") // 用於 Oracle、PostgreSQL、Kingbase、DB2、H2 資料庫的主鍵自增。如果是 MySQL 等資料庫，可不寫。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StarRatingDO extends BaseDO {

    /**
     * 評分編號
     */
    @TableId
    private Long id;
    /**
     * 菜品編號
     */
    private Long dishId;
    /**
     * 用戶編號
     */
    private Long userId;
    /**
     * 星級評分（1-5星）
     */
    private Integer starLevel;
    /**
     * 評價內容
     */
    private String comment;

} 