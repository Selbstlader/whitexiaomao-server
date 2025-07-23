package cn.iocoder.yudao.module.cooking.dal.mysql;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.cooking.dal.dataobject.StepDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 烹飪步驟 Mapper
 *
 * @author 芋道源碼
 */
@Mapper
public interface StepMapper extends BaseMapperX<StepDO> {

    default List<StepDO> selectListByDishId(Long dishId) {
        return selectList(new LambdaQueryWrapperX<StepDO>()
                .eq(StepDO::getDishId, dishId)
                .orderByAsc(StepDO::getStepNumber));
    }

    default StepDO selectByDishIdAndStepNumber(Long dishId, Integer stepNumber) {
        return selectOne(new LambdaQueryWrapperX<StepDO>()
                .eq(StepDO::getDishId, dishId)
                .eq(StepDO::getStepNumber, stepNumber));
    }

    default int deleteByDishId(Long dishId) {
        return delete(StepDO::getDishId, dishId);
    }
} 