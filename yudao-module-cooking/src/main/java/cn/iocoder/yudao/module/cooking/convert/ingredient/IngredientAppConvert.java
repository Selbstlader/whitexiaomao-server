package cn.iocoder.yudao.module.cooking.convert.ingredient;

import cn.iocoder.yudao.module.cooking.controller.app.ingredient.vo.IngredientRespVO;
import cn.iocoder.yudao.module.cooking.dal.dataobject.IngredientDO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 烹飪配料 App Convert
 *
 * @author 芋道源碼
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IngredientAppConvert {

    IngredientAppConvert INSTANCE = Mappers.getMapper(IngredientAppConvert.class);

    IngredientRespVO convert(IngredientDO bean);

    List<IngredientRespVO> convertList(List<IngredientDO> list);
}