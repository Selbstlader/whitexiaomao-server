package cn.iocoder.yudao.module.cooking.dal.dataobject.category;

import cn.iocoder.yudao.framework.tenant.core.aop.TenantIgnore;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜品分类 DO
 *
 * @author 芋道源码
 */
@TableName("cooking_category")
@KeySequence("categories_seq")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TenantIgnore
public class CategoryDO {

    /**
     * 分类编号
     */
    @TableId
    private Long id;
    /**
     * 分类名称
     */
    private String name;
}