package cn.iocoder.yudao.module.cooking.dal.mysql;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.cooking.dal.dataobject.TipDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 烹飪小貼士 Mapper
 *
 * @author 芋道源碼
 */
@Mapper
public interface TipMapper extends BaseMapperX<TipDO> {

    default List<TipDO> selectListByDishId(Long dishId) {
        return selectList(new LambdaQueryWrapperX<TipDO>()
                .eq(TipDO::getDishId, dishId)
                .orderByAsc(TipDO::getId));
    }

    default int deleteByDishId(Long dishId) {
        return delete(TipDO::getDishId, dishId);
    }
} 