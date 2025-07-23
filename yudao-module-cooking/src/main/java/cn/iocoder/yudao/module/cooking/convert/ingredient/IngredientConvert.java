package cn.iocoder.yudao.module.cooking.convert.ingredient;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.cooking.controller.admin.ingredient.vo.*;
import cn.iocoder.yudao.module.cooking.dal.dataobject.IngredientDO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 配料 Convert
 *
 * @author 芋道源碼
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IngredientConvert {

    IngredientConvert INSTANCE = Mappers.getMapper(IngredientConvert.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "updater", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "transMap", ignore = true)
    IngredientDO convert(IngredientCreateReqVO bean);

    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "updater", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "transMap", ignore = true)
    IngredientDO convert(IngredientUpdateReqVO bean);

    @Named("convert")
    IngredientRespVO convert(IngredientDO bean);

    @IterableMapping(qualifiedByName = "convert")
    List<IngredientRespVO> convertList(List<IngredientDO> list);

    /**
     * 分頁結果轉換
     * 
     * @param page 分頁結果
     * @return 轉換後的分頁結果
     */
    default PageResult<IngredientRespVO> convertPage(PageResult<IngredientDO> page) {
        if (page == null) {
            return null;
        }
        List<IngredientRespVO> respList = convertList(page.getList());
        return new PageResult<>(respList, page.getTotal());
    }

    /**
     * 將創建批量請求轉換為DO列表
     *
     * @param reqVO 批量創建請求
     * @return DO列表
     */
    default List<IngredientDO> convertList(IngredientBatchCreateReqVO reqVO) {
        if (reqVO == null || reqVO.getIngredients() == null) {
            return null;
        }
        return reqVO.getIngredients().stream()
                .map(item -> {
                    IngredientDO ingredientDO = new IngredientDO();
                    ingredientDO.setDishId(reqVO.getDishId());
                    ingredientDO.setName(item.getName());
                    ingredientDO.setAmount(item.getAmount());
                    return ingredientDO;
                })
                .toList();
    }
} 