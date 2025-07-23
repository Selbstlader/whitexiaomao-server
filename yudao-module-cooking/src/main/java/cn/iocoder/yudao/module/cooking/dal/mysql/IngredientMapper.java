package cn.iocoder.yudao.module.cooking.dal.mysql;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.cooking.dal.dataobject.IngredientDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 配料 Mapper
 *
 * @author 芋道源碼
 */
@Mapper
public interface IngredientMapper extends BaseMapperX<IngredientDO> {

    default List<IngredientDO> selectListByDishId(Long dishId) {
        return selectList(IngredientDO::getDishId, dishId);
    }

    default IngredientDO selectByDishIdAndName(Long dishId, String name) {
        return selectOne(new LambdaQueryWrapperX<IngredientDO>()
                .eq(IngredientDO::getDishId, dishId)
                .eq(IngredientDO::getName, name));
    }

    default int deleteByDishId(Long dishId) {
        return delete(IngredientDO::getDishId, dishId);
    }
} 