package cn.iocoder.yudao.module.cooking.dal.mysql;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.cooking.dal.dataobject.StarRatingDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜品星級評分 Mapper
 *
 * @author 芋道源碼
 */
@Mapper
public interface StarRatingMapper extends BaseMapperX<StarRatingDO> {

    default List<StarRatingDO> selectListByDishId(Long dishId) {
        return selectList(new LambdaQueryWrapperX<StarRatingDO>()
                .eq(StarRatingDO::getDishId, dishId)
                .orderByDesc(StarRatingDO::getStarLevel));
    }

    default StarRatingDO selectByDishIdAndUserId(Long dishId, Long userId) {
        return selectOne(new LambdaQueryWrapperX<StarRatingDO>()
                .eq(StarRatingDO::getDishId, dishId)
                .eq(StarRatingDO::getUserId, userId));
    }

    default int deleteByDishId(Long dishId) {
        return delete(StarRatingDO::getDishId, dishId);
    }
} 