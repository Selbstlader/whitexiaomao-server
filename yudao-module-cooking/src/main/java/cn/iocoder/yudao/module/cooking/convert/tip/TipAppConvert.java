package cn.iocoder.yudao.module.cooking.convert.tip;

import cn.iocoder.yudao.module.cooking.controller.app.tip.vo.TipRespVO;
import cn.iocoder.yudao.module.cooking.dal.dataobject.TipDO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 烹飪小貼士 App Convert
 *
 * @author 芋道源碼
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TipAppConvert {

    TipAppConvert INSTANCE = Mappers.getMapper(TipAppConvert.class);

    TipRespVO convert(TipDO bean);

    List<TipRespVO> convertList(List<TipDO> list);
}