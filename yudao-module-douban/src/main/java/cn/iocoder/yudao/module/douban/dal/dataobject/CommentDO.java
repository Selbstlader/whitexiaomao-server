package cn.iocoder.yudao.module.douban.dal.dataobject;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 豆瓣电影评论 DO
 *
 * @author 芋道源码
 */
@TableName("douban_comments")
@KeySequence("douban_comments_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CommentDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    
    /**
     * 短评唯一ID
     */
    private String commentId;
    
    /**
     * 电影ID
     */
    private String movieId;
    
    /**
     * 评论者用户名
     */
    private String people;
    
    /**
     * 评论者页面URL
     */
    private String peopleUrl;
    
    /**
     * 评分（1-5星）
     */
    private Integer star;
    
    /**
     * 评论内容
     */
    private String content;
    
    /**
     * 有用数
     */
    private Integer usefulNum;
    
    /**
     * 评论时间
     */
    private String commentTime;
    
    /**
     * 短评页面URL
     */
    private String pageUrl;
}