package cn.iocoder.yudao.module.cooking.convert.step;

import cn.iocoder.yudao.module.cooking.controller.app.step.vo.StepRespVO;
import cn.iocoder.yudao.module.cooking.dal.dataobject.StepDO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 烹飪步驟 App Convert
 *
 * @author 芋道源碼
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StepAppConvert {

    StepAppConvert INSTANCE = Mappers.getMapper(StepAppConvert.class);

    StepRespVO convert(StepDO bean);

    List<StepRespVO> convertList(List<StepDO> list);
}