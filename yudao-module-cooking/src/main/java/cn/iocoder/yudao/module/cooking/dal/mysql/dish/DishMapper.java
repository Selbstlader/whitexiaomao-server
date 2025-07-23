package cn.iocoder.yudao.module.cooking.dal.mysql.dish;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.cooking.controller.admin.dish.vo.DishPageReqVO;
import cn.iocoder.yudao.module.cooking.dal.dataobject.DishDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * 菜品 Mapper
 *
 * @author 芋道源碼
 */
@Mapper
public interface DishMapper extends BaseMapperX<DishDO> {

    default DishDO selectByName(String name) {
        return selectOne(DishDO::getName, name);
    }

    default List<DishDO> selectByCategoryId(Long categoryId) {
        return selectList(DishDO::getCategoryId, categoryId);
    }

    default PageResult<DishDO> selectPage(DishPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DishDO>()
                .likeIfPresent(DishDO::getName, reqVO.getName())
                .eqIfPresent(DishDO::getCategoryId, reqVO.getCategoryId())
                .eqIfPresent(DishDO::getDifficulty, reqVO.getDifficulty())
                .orderByDesc(DishDO::getId));
    }
    
    default List<DishDO> selectBatchByIds(Collection<Long> ids) {
        return selectList(new LambdaQueryWrapperX<DishDO>()
                .in(DishDO::getId, ids));
    }

} 