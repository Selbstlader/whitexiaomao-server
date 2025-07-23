package cn.iocoder.yudao.module.cooking.convert.tip;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.cooking.controller.admin.tip.vo.*;
import cn.iocoder.yudao.module.cooking.dal.dataobject.TipDO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 烹飪小貼士 Convert
 *
 * @author 芋道源碼
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TipConvert {

    TipConvert INSTANCE = Mappers.getMapper(TipConvert.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "updater", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "transMap", ignore = true)
    TipDO convert(TipCreateReqVO bean);

    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "updater", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "transMap", ignore = true)
    TipDO convert(TipUpdateReqVO bean);

    @Named("convert")
    TipRespVO convert(TipDO bean);

    @IterableMapping(qualifiedByName = "convert")
    List<TipRespVO> convertList(List<TipDO> list);

    /**
     * 分頁結果轉換
     * 
     * @param page 分頁結果
     * @return 轉換後的分頁結果
     */
    default PageResult<TipRespVO> convertPage(PageResult<TipDO> page) {
        if (page == null) {
            return null;
        }
        List<TipRespVO> respList = convertList(page.getList());
        return new PageResult<>(respList, page.getTotal());
    }

    /**
     * 將創建批量請求轉換為DO列表
     *
     * @param reqVO 批量創建請求
     * @return DO列表
     */
    default List<TipDO> convertList(TipBatchCreateReqVO reqVO) {
        if (reqVO == null || reqVO.getTips() == null) {
            return null;
        }
        return reqVO.getTips().stream()
                .map(item -> {
                    TipDO tipDO = new TipDO();
                    tipDO.setDishId(reqVO.getDishId());
                    tipDO.setContent(item.getContent());
                    return tipDO;
                })
                .toList();
    }
} 