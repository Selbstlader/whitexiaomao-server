package cn.iocoder.yudao.module.cooking.dal.mysql.ingredient;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.cooking.controller.admin.ingredient.vo.IngredientExportReqVO;
import cn.iocoder.yudao.module.cooking.controller.admin.ingredient.vo.IngredientPageReqVO;
import cn.iocoder.yudao.module.cooking.dal.dataobject.ingredient.IngredientDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 食材 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface IngredientMapper extends BaseMapperX<IngredientDO> {

    default PageResult<IngredientDO> selectPage(IngredientPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<IngredientDO>()
                .likeIfPresent(IngredientDO::getName, reqVO.getName())
                .eqIfPresent(IngredientDO::getCategoryId, reqVO.getCategoryId())
                .eqIfPresent(IngredientDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(IngredientDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(IngredientDO::getId));
    }

    default List<IngredientDO> selectList(IngredientExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<IngredientDO>()
                .likeIfPresent(IngredientDO::getName, reqVO.getName())
                .eqIfPresent(IngredientDO::getCategoryId, reqVO.getCategoryId())
                .eqIfPresent(IngredientDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(IngredientDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(IngredientDO::getId));
    }

    default List<IngredientDO> selectListByCategory(Long categoryId) {
        return selectList(new LambdaQueryWrapperX<IngredientDO>()
                .eq(IngredientDO::getCategoryId, categoryId)
                .eq(IngredientDO::getStatus, 1) // 只查询启用的食材
                .orderByAsc(IngredientDO::getName));
    }

    default List<IngredientDO> searchIngredients(String keyword) {
        return selectList(new LambdaQueryWrapperX<IngredientDO>()
                .like(IngredientDO::getName, keyword)
                .or()
                .like(IngredientDO::getDescription, keyword)
                .eq(IngredientDO::getStatus, 1) // 只搜索启用的食材
                .orderByAsc(IngredientDO::getName));
    }

}
