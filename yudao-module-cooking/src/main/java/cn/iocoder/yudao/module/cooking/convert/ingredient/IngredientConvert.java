package cn.iocoder.yudao.module.cooking.convert.ingredient;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.cooking.controller.app.vo.ingredient.IngredientRespVO;
import cn.iocoder.yudao.module.cooking.dal.dataobject.ingredient.IngredientDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 食材 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface IngredientConvert {

    IngredientConvert INSTANCE = Mappers.getMapper(IngredientConvert.class);

    /**
     * IngredientDO 转 IngredientRespVO
     */
    IngredientRespVO convert(IngredientDO bean);

    /**
     * IngredientDO 列表转 IngredientRespVO 列表
     */
    List<IngredientRespVO> convertList(List<IngredientDO> list);

    /**
     * 分页结果转换
     */
    PageResult<IngredientRespVO> convertPage(PageResult<IngredientDO> page);

    /**
     * APP端的查询VO转换为Admin端的查询VO
     */
    cn.iocoder.yudao.module.cooking.controller.admin.ingredient.vo.IngredientPageReqVO convert(
            cn.iocoder.yudao.module.cooking.controller.app.vo.ingredient.IngredientPageReqVO pageReqVO);
} 