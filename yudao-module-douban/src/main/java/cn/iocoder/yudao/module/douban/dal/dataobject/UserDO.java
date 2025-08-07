package cn.iocoder.yudao.module.douban.dal.dataobject;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 豆瓣用户 DO
 *
 * @author 芋道源码
 */
@TableName("douban_users")
@KeySequence("douban_users_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    
    /**
     * 用户名
     */
    private String people;
    
    /**
     * 常居地
     */
    private String location;
    
    /**
     * 个人简介
     */
    private String introduction;
    
    /**
     * 好友数
     */
    private Integer friendCount;
    
    /**
     * 被关注数
     */
    private Integer beAttentionCount;
}