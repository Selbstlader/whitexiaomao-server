package cn.iocoder.yudao.module.cooking.dal.mysql.category;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.cooking.dal.dataobject.category.CategoryDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 分类 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface CategoryMapper extends BaseMapperX<CategoryDO> {

    default List<CategoryDO> selectListByType(Integer type) {
        return selectList(new LambdaQueryWrapperX<CategoryDO>()
                .eq(CategoryDO::getType, type)
                .eq(CategoryDO::getStatus, 1) // 只查询启用的分类
                .orderByAsc(CategoryDO::getSort)
                .orderByAsc(CategoryDO::getId));
    }

    default List<CategoryDO> selectListByParent(Long parentId) {
        return selectList(new LambdaQueryWrapperX<CategoryDO>()
                .eq(CategoryDO::getParentId, parentId)
                .eq(CategoryDO::getStatus, 1) // 只查询启用的分类
                .orderByAsc(CategoryDO::getSort)
                .orderByAsc(CategoryDO::getId));
    }

    default List<CategoryDO> selectTreeList(Integer type) {
        return selectList(new LambdaQueryWrapperX<CategoryDO>()
                .eq(CategoryDO::getType, type)
                .eq(CategoryDO::getStatus, 1) // 只查询启用的分类
                .orderByAsc(CategoryDO::getSort)
                .orderByAsc(CategoryDO::getId));
    }

    default boolean hasChildren(Long parentId) {
        return selectCount(new LambdaQueryWrapperX<CategoryDO>()
                .eq(CategoryDO::getParentId, parentId)) > 0;
    }

}
