package cn.iocoder.yudao.module.douban.dal.dataobject;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 豆瓣电影 DO
 *
 * @author 芋道源码
 */
@TableName("douban_movies")
@KeySequence("douban_movies_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MovieDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    
    /**
     * 电影唯一ID
     */
    private String movieId;
    
    /**
     * 电影名称
     */
    private String movieTitle;
    
    /**
     * 发布年份
     */
    private Integer releaseDate;
    
    /**
     * 导演（JSON格式）
     */
    private String directedBy;
    
    /**
     * 主演（JSON格式）
     */
    private String starring;
    
    /**
     * 电影类型（JSON格式）
     */
    private String genre;
    
    /**
     * 电影时长（分钟）
     */
    private Integer runtime;
    
    /**
     * 出品国家
     */
    private String country;
    
    /**
     * 语言
     */
    private String language;
    
    /**
     * 总评分
     */
    private BigDecimal ratingNum;
    
    /**
     * 评分人数
     */
    private Integer voteNum;
    
    /**
     * 5星百分比
     */
    private BigDecimal ratingPerStars5;
    
    /**
     * 4星百分比
     */
    private BigDecimal ratingPerStars4;
    
    /**
     * 3星百分比
     */
    private BigDecimal ratingPerStars3;
    
    /**
     * 2星百分比
     */
    private BigDecimal ratingPerStars2;
    
    /**
     * 1星百分比
     */
    private BigDecimal ratingPerStars1;
    
    /**
     * 电影简介
     */
    private String intro;
    
    /**
     * 短评数
     */
    private Integer commentNum;
    
    /**
     * 提问数
     */
    private Integer questionNum;
}