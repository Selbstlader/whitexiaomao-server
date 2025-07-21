package cn.iocoder.yudao.module.cooking.dal.mysql.recipe;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.cooking.controller.admin.recipe.vo.RecipeExportReqVO;
import cn.iocoder.yudao.module.cooking.controller.admin.recipe.vo.RecipePageReqVO;
import cn.iocoder.yudao.module.cooking.dal.dataobject.recipe.RecipeDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜谱 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface RecipeMapper extends BaseMapperX<RecipeDO> {

    default PageResult<RecipeDO> selectPage(RecipePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RecipeDO>()
                .likeIfPresent(RecipeDO::getName, reqVO.getName())
                .eqIfPresent(RecipeDO::getCategoryId, reqVO.getCategoryId())
                .eqIfPresent(RecipeDO::getDifficulty, reqVO.getDifficulty())
                .eqIfPresent(RecipeDO::getStatus, reqVO.getStatus())
                .eqIfPresent(RecipeDO::getCreatorId, reqVO.getCreatorId())
                .betweenIfPresent(RecipeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RecipeDO::getId));
    }

    default List<RecipeDO> selectList(RecipeExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<RecipeDO>()
                .likeIfPresent(RecipeDO::getName, reqVO.getName())
                .eqIfPresent(RecipeDO::getCategoryId, reqVO.getCategoryId())
                .eqIfPresent(RecipeDO::getDifficulty, reqVO.getDifficulty())
                .eqIfPresent(RecipeDO::getStatus, reqVO.getStatus())
                .eqIfPresent(RecipeDO::getCreatorId, reqVO.getCreatorId())
                .betweenIfPresent(RecipeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RecipeDO::getId));
    }

    default List<RecipeDO> selectListByCategory(Long categoryId) {
        return selectList(new LambdaQueryWrapperX<RecipeDO>()
                .eq(RecipeDO::getCategoryId, categoryId)
                .eq(RecipeDO::getStatus, 1) // 只查询已发布的菜谱
                .orderByDesc(RecipeDO::getId));
    }

    default List<RecipeDO> selectListByCreator(Long creatorId) {
        return selectList(new LambdaQueryWrapperX<RecipeDO>()
                .eq(RecipeDO::getCreatorId, creatorId)
                .orderByDesc(RecipeDO::getId));
    }

    default List<RecipeDO> searchRecipes(String keyword) {
        return selectList(new LambdaQueryWrapperX<RecipeDO>()
                .like(RecipeDO::getName, keyword)
                .or()
                .like(RecipeDO::getDescription, keyword)
                .or()
                .like(RecipeDO::getTags, keyword)
                .eq(RecipeDO::getStatus, 1) // 只搜索已发布的菜谱
                .orderByDesc(RecipeDO::getId));
    }

}
