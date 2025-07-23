package cn.iocoder.yudao.module.cooking.convert.step;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.cooking.controller.admin.step.vo.*;
import cn.iocoder.yudao.module.cooking.dal.dataobject.StepDO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 烹飪步驟 Convert
 *
 * @author 芋道源碼
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StepConvert {

    StepConvert INSTANCE = Mappers.getMapper(StepConvert.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "updater", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "transMap", ignore = true)
    StepDO convert(StepCreateReqVO bean);

    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "updater", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "transMap", ignore = true)
    StepDO convert(StepUpdateReqVO bean);

    @Named("convert")
    StepRespVO convert(StepDO bean);

    @IterableMapping(qualifiedByName = "convert")
    List<StepRespVO> convertList(List<StepDO> list);

    /**
     * 分頁結果轉換
     * 
     * @param page 分頁結果
     * @return 轉換後的分頁結果
     */
    default PageResult<StepRespVO> convertPage(PageResult<StepDO> page) {
        if (page == null) {
            return null;
        }
        List<StepRespVO> respList = convertList(page.getList());
        return new PageResult<>(respList, page.getTotal());
    }

    /**
     * 將創建批量請求轉換為DO列表
     *
     * @param reqVO 批量創建請求
     * @return DO列表
     */
    default List<StepDO> convertList(StepBatchCreateReqVO reqVO) {
        if (reqVO == null || reqVO.getSteps() == null) {
            return null;
        }
        return reqVO.getSteps().stream()
                .map(item -> {
                    StepDO stepDO = new StepDO();
                    stepDO.setDishId(reqVO.getDishId());
                    stepDO.setStepNumber(item.getStepNumber());
                    stepDO.setDescription(item.getDescription());
                    return stepDO;
                })
                .toList();
    }
} 